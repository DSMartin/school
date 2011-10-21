//******************************************************************************
//**  File: testpipe.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy 
//**
//**  Description: A simple C++ program to demonstrate a Linux pipe.
//**    Create a pipe and write "hello" via the pipe to STDOUT
//**    (adapted from Munehiro Fukuda & http://linux.die.net/man/2/pipe)
//******************************************************************************
#include <sys/types.h>   // for fork, wait
#include <sys/wait.h>    // for wait
#include <unistd.h>      // for fork, pipe, dup, close
#include <stdio.h>       // for NULL, perror
#include <iostream>      // for cout

using namespace std;

const int MAXLINE = 80;

int main( int argc, char** argv ) {
  int n, fd[2];
  int pid;
  char line[MAXLINE];
  
  if ( pipe(fd) < 0 ) { // 1: create pipe
    perror("pipe"); exit(EXIT_FAILURE); 
  } else if ( ( pid = fork() ) < 0 ) { // 2: spawn child
    perror("fork"); exit(EXIT_FAILURE); 
  } else if ( pid > 0 ) { // parent
    close( fd[0] );     // 3: parent's fd[0] closed
    write( fd[1], "hello\n", 6 );  // write to the pipe's output fd
    close( fd[1] );     // close pipe's output fd (no more output)
    wait( NULL ); // wait for child
    exit( EXIT_SUCCESS );
  } else {              // child
    close( fd[1] );     // 4: child's fd[1] closed
    n = read( fd[0], line, MAXLINE );   // read a line from the pipe's input fd
    write( 1, line, n );  // write to STDOUT
    close( fd[0] );   // close pipe's input fd (no more input)
    exit( EXIT_SUCCESS );
  }
}
