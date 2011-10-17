// child.cpp:
// by Munehiro Fukuda
//
// This program is spawned by the parent.cpp process.
// It assumes that a file has been opened and accessible with fd = 3.
// It keeps reading the file contents together with the parent.cpp.

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include <iostream>

using namespace std;

int main( ) {
  int fd;

  fd = 3;
  cout << "Child2 process[" << getpid( ) << "] reuses fd = " << fd << endl;

  char buffer[4];
  buffer[3] = 0;

  while ( read( fd, buffer, 3 ) != 0 ) {
    cout << "Child2 process[" << getpid( ) << "] has read " << buffer << endl;
    sleep( 1 );
  }

  close( fd );
}
