//******************************************************************************
//**  File: testpipe3.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy 
//**
//**  Description: A simple C++ program to demonstrate a Linux pipe.
//**    Create a pipe and use echo to pass a string arg through the pipe to STDOUT
//**    NOTE: This program hangs; can you see why?
//**    (adapted from Munehiro Fukuda & http://linux.die.net/man/2/pipe)
//******************************************************************************
#include <sys/types.h>   // for fork, wait
#include <sys/wait.h>    // for wait
#include <unistd.h>      // for fork, pipe, dup, close
#include <stdio.h>       // for NULL, perror
#include <iostream>      // for cout
#include <assert.h>      // for assert

using namespace std;

int main( int argc, char** argv ) {
  int fd[2];
  int pid;      // only need one pid (why?)

  assert(argc == 2);    // argv[0] = command, argv[1] is string to be echo'ed thru pipe
  
  if ( pipe(fd) < 0 ) { 
    perror( "pipe" ); exit( EXIT_FAILURE ); 
  } else if ( ( pid = fork() ) < 0 ) { 
    perror( "fork" ); exit( EXIT_FAILURE ); 
  } else if ( pid > 0 ) { // this is the parent
    wait( NULL );
    exit( EXIT_SUCCESS );
  } else if ( ( pid = fork() ) < 0 ) {  
    perror( "fork" ); exit( EXIT_FAILURE ); 
  } else if ( pid > 0 ) { // this is [still] the child
    dup2( fd[0], 0 ); // copy read-end of pipe to STDIN
    close( fd[1] );   // close write-end of pipe (not needed)
    execlp( "cat", "cat", NULL );
  } else {  // this is the grand-child
    close( fd[0] );   // close read-end of pipe (not needed)
    dup2( fd[1], 1 ); // copy write-end of pipe to STDOUT
    execlp( "echo", "echo", argv[1], NULL );
  }
}
