//******************************************************************************
//**  File: pipedup2f.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy
//**          (adapted from code originally written by Munehiro Fukuda)
//**
//**  Description: A demonstration of using the pipe and dup2 system calls.
//**    The program emulates 'ps -A | tr a-z A-Z'
//**    The parent executes ps, writing to the pipe; 
//**      after a short sleep, the child executes tr, reading from the pipe.
//**    The parent & child code is the reverse of who does what in pipedup2e.cpp
//**    The original pipe file descriptors are closed after making copies
//**      via dup2 and assigning them to STDIN and STDOUT in parent & child,
//**      freeing up the file descriptors for other potential uses;
//**      based on http://www.scs.ryerson.ca/%7Ernagendr/590lecture.pdf
//**      (via CSS 430 student Darren Korman)
//******************************************************************************

#include <iostream>    // cin, cout
#include <sys/types.h> // fork
#include <unistd.h>    // pipe, close, read, dup2, fork, execlp
#include <stdio.h>     // perror

#define MAXLINE 80

using namespace std;

int main( ) {
  int fd[2];           // fd[0]: pipe input, fd[1]: pipe output
  int pid;             // process id

  if ( pipe( fd ) < 0 ) {
    perror( "pipe error" );
    exit( EXIT_FAILURE );
  } 
  if ( ( pid = fork() ) < 0 ) {
    perror ( "fork error" );
    exit( EXIT_FAILURE );
  }
  if ( pid > 0 ) {    // Parent
    close( fd[0] );   // close read-end of pipe
    dup2( fd[1], 1 ); // STDOUT gets copy of write-end of pipe
    close( fd[1] );   // close original write-end of pipe
    execlp( "ps", "ps", "-A", NULL );
    perror( "execlp (ps)" );  // should not get here
    exit( EXIT_FAILURE );     // unless execlp failed
  } else {            // pid == 0, Child
    close( fd[1] );   // close read-end of pipe
    dup2( fd[0], 0 ); // STDIN gets copy of read-end of pipe
    close( fd[0] );   // close original read-end of pipe
    sleep( 2 );
    execlp( "tr", "tr", "a-z", "A-Z", NULL );
    perror( "execlp (tr)" );  // should not get here
    exit( EXIT_FAILURE );     // unless execlp failed
  }
}
