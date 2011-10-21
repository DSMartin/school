// 
//  processes.cpp
//  CSS430
//  
//  Created by John Hildebrant on 2011-10-18.
//
//  Description: A demonstration of using the pipe and dup2 system calls.
//    The program emulates 'ps -A | grep argv[1] | wc -l'
// 
#include <stdio.h>                  // perror
#include <sys/types.h>              // fork
#include <unistd.h>                 // pipe, close, read, dup2, fork, execlp
#include <sys/wait.h>               // wait
#include <assert.h>                 // for assert
#include <stdlib.h>                 // EXIT_FAILURE, EXIT_SUCCESS

using namespace std;

int main(int argc, char** argv) {
  int fd[2], fd2[2];                // two pipes- fd[0]: pipe input, 
                                    // fd[1]: pipe output        
  int pid, pid2, pid3;              // process id
  
  assert(argc == 2);                // argv[0] = executable, argv[1] is argument
  
  // set up pipes
  if (pipe(fd) < 0 || pipe(fd2) < 0) {
    perror("pipe error");
    exit( EXIT_FAILURE );
  }
  if ((pid = fork()) < 0) {         // fork child
    perror("fork error");
    exit( EXIT_FAILURE );
  }
  
  if (pid == 0) {                   // Child
    close(fd2[1]);                  // close write-end of both pipes
    close(fd[1]);
    dup2(fd2[0], 0);                // STDIN gets copy of read-end of 2nd pipe
    close(fd2[0]);                  // close original read-end of 2nd pipe
    execlp("wc", "wc", "-l", NULL);
    perror("execlp (wc)");          // should not get here 
    _exit( EXIT_FAILURE );          // unless execlp failed
  } else {
    if ((pid2 = fork()) < 0) {      // fork grand-child
      perror("pipe error");
      exit( EXIT_FAILURE );
    }
    if (pid2 == 0) {                // Grand-child
      close(fd[1]);                 // close write-end of first pipe
      close(fd2[0]);                // close read-end of second pipe
      dup2(fd[0], 0);               // STDIN gets copy of read-end of 1st pipe
      close(fd[0]);                 // close original read-end of 1st pipe
      dup2(fd2[1], 1);              // STDOUT gets copy of write-end of 2nd pipe
      close(fd2[1]);                // close original write-end of 2nd pipe
      execlp("grep", "grep", argv[1], NULL);
      perror("execlp (grep)");      // should not get here 
      _exit( EXIT_FAILURE );        // unless execlp failed
    } else {
      if ((pid3 = fork()) < 0) {    // fork great-grand-child
        perror("pipe error");
        exit( EXIT_FAILURE );
      }
      if (pid3 == 0) {              // Great-grand-child
        close(fd[0]);               // close read-end of 1st pipe
        close(fd2[1]);              // close write-end of 2nd pipe
        dup2(fd[1], 1);             // STDOUT gets copy of write-end of 2nd pipe
        close(fd[1]);               // close original write-end of 2nd pipe
        execlp("ps", "ps", "-A", NULL);
        perror("execlp (ps)");      // should not get here
        _exit( EXIT_FAILURE );      // unless execlp failed
      } else {                      // Parent
        wait(NULL);                 // wait for child
        exit( EXIT_SUCCESS );
      }
    }
  }
}
 
