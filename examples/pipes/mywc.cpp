//******************************************************************************
//**  File: mywc.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy
//**
//**  Description: A demonstration of using the fork and wait system calls.
//**    The program emulates 'wc -l'
//******************************************************************************

#include <iostream>    // cin, cout
#include <sys/types.h> // fork
#include <sys/wait.h>  // wait
#include <unistd.h>    // pipe, close, read, dup2, fork, execlp
#include <stdio.h>     // perror

using namespace std;

int main( ) {
  int fd[2];           // fd[0]: pipe input, fd[1]: pipe output
  int pid;             // process id

  if ( ( pid = fork() ) < 0 ) {
    perror ( "fork (1)" );
    exit( EXIT_FAILURE );
  } 
  if ( pid > 0 ) {      // Parent
    wait( NULL );       // wait for Child
    exit( EXIT_SUCCESS );
  } else {              // Child
    execlp( "wc", "wc", "-l", NULL );
    perror( "execlp (wc)" );  // should not get here
    exit( EXIT_FAILURE );     // unless execlp failed
  } 
}
