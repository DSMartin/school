//******************************************************************************
//**  File: pipedup2a.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy
//**          (adapted from code originally written by Munehiro Fukuda)
//**
//**  Description: A demonstration of using the pipe and dup2 system calls.
//**    The program emulates 'ps -A | tr a-z A-Z'
//**    tr is not used, but an equivalent loop is used
//**    After a short sleep, parent implements tr-like loop, reading from pipe; 
//**      the child executes ps, writing to the pipe.
//**    The parent & child code is the reverse of who does what in pipedup2b.cpp
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
  char line[MAXLINE];  // message from fd[0]
  int nread;           // #bytes read

  if ( pipe( fd ) < 0 ) {
    perror( "pipe error" );
    exit( EXIT_FAILURE );
  }
  if ( ( pid = fork() ) < 0 ) {
    perror ( "fork error" );
    exit( EXIT_FAILURE );
  } 
  if ( pid > 0 ) {  // Parent
    close( fd[1] );
    sleep( 2 );
    while ( ( nread = read( fd[0], line, MAXLINE ) ) > 0 ) {
      for ( int i = 0; i < nread; i++ ) {
        if ( line[i] >= 'a' && line[i] <= 'z' )
          line[i] += 'A' - 'a';   // convert letters from lower to upper
        cout << line[i];
      }
    }
    close( fd[0] );
    exit( EXIT_SUCCESS );
  } else {        // pid == 0, Child
    close( fd[0] );
    dup2( fd[1], 1 );
    execlp( "ps", "ps", "-A", NULL );
  }
}
