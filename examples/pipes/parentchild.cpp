//******************************************************************************
//**  File: testpipe.cpp
//**  Date: October 2011
//**  Author: Joe McCarthy 
//**
//**  Description: A simple C++ program to demonstrate fork.
//**    For each iteration (i) of a loop, fork a child and have child & parent print i
//**    (adapted from http://www.cis.temple.edu/~ingargio/cis307/readings/unix1.html)
//******************************************************************************
#include <sys/types.h>   // for fork, wait
#include <unistd.h>      // for fork, pipe, dup, close
#include <stdio.h>       // for NULL, perror
#include <iostream>      // for cout

using namespace std;

int main( int argc, char** argv ) {
  int i;
  int pid;  
  
  for ( i = 0; i < 3; i++ ) {
    if ( ( pid = fork() ) < 0 ) {
      perror("fork"); 
      exit( EXIT_FAILURE ); 
    } else if ( pid > 0 ) {
      cout << "Parent: " << i << " (pid = " << pid << ")" << endl;
    } else { 
      cout << "Child: " << i << " (pid = " << pid << ")" << endl;
    }
  }
  exit( EXIT_SUCCESS );
}
