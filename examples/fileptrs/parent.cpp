// parent.cpp: 
// by Munehiro Fukuda
//
// This program spawns two children.
// The parent and the first child runs in the same program: parent.cpp
// The second child runs in the child.cpp.
// However, all those processes share the same file descriptor #3 and
// increments the same file pointer in concurrent.

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include <iostream>

using namespace std;

int main( ) {
  int fd;

  fd = open( "sample.dat", O_RDONLY );  // The parent process opens a file.
  cout << "Parent process[" << getpid( ) << "] has opend fd = " << fd << endl;

  char buffer[4];                       // allocates a buffer
  buffer[3] = 0;

  int child = fork( );                  // the first child was born.

  // The parent and the first child repeat reading the shared file
  for ( int i =0;  read( fd, buffer, 3 ) != 0; i++ ) {
    if ( child != 0 )
      cout << "Parent ";
    else
      cout << "Child1 ";
    cout << "process[" << getpid( ) << "] has read " << buffer << endl;
    sleep( 1 );

    // The parent spawns the second child at the 10th iteration.
    if ( i == 10 && child != 0 ) {
      if ( fork( ) == 0 ) {             // I'm the second child
	execl( "child", NULL );         // The second child overwrites with
      }                                 // child.cpp
    }
  }

  close( fd );
}
