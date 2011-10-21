//******************************************************************************
//**  File: pipedup2d.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy
//**          (adapted from code originally written by Munehiro Fukuda)
//**
//**  Description: A demonstration of using the pipe and dup2 system calls.
//**    The program emulates 'ps -A | tr a-z A-Z'
//**    The parent executes ps, writing to the pipe; 
//**      after a short sleep, the child executes tr, reading from the pipe.
//**    The parent & child code is the reverse of who does what in pipedup2c.cpp
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
  if ( pid > 0 ) {  // Parent
    close( fd[0] );
    dup2( fd[1], 1 );
    execlp( "ps", "ps", "-A", NULL );
  } else {          // pid == 0, Child
    close( fd[1] );
    dup2( fd[0], 0 );
    sleep( 2 );
    execlp( "tr", "tr", "a-z", "A-Z", NULL );
  }
}
