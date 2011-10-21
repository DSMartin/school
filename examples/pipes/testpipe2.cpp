//******************************************************************************
//**  File: testpipe.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy 
//**
//**  Description: A simple C++ program to demonstrate a Linux pipe.
//**    Create a pipe and use echo to pass a string arg through the pipe to STDOUT
//**    (adapted from Munehiro Fukuda & http://linux.die.net/man/2/pipe)
//******************************************************************************
#include <sys/types.h>   // for fork, wait
#include <sys/wait.h>    // for wait
#include <unistd.h>      // for fork, pipe, dup, close
#include <stdio.h>       // for NULL, perror
#include <iostream>      // for cout
#include <assert.h>      // for assert

using namespace std;

const int MAXLINE = 80;

int main( int argc, char** argv ) {
  int n, fd[2];
  int pid;
  char buf;

  assert(argc == 2);    // argv[0] = a.out, argv[1] is string to be piped
  
  if ( pipe(fd) < 0 ) { // 1: attempt to create pipe
    perror("pipe"); exit(EXIT_FAILURE); 
  } else if ( ( pid = fork() ) < 0 ) { // 2: attempt to spawn child
    perror("fork"); exit(EXIT_FAILURE); 
  } else if ( pid > 0 ) { // this is the parent
    close( fd[0] );     // 3: parent's fd[0] closed (not reading from pipe)
    execlp( "echo", "echo", argv[1], NULL );
    // execlp causes parent process to be overlaid by 'echo'
    // no need for close() or exit()
  } else {              // this is the child
    close( fd[1] );     // 4: child's fd[1] closed (not writing to pipe)
    while ( read(fd[0], &buf, 1) > 0 )  // read a char from pipe
      write(STDOUT_FILENO, &buf, 1);    // write a char to STDOUT
    close( fd[0] );   // close pipe's input fd (no more input)
    exit( EXIT_SUCCESS );
  }
}
