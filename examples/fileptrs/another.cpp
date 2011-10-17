// another.cpp: 
// by Munehiro Fukuda
//
// This program runs independent of parent.cpp. It just reads the same file
// "sample.dat" independently.

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include <iostream>

using namespace std;

int main( ) {
  int fd;

  fd = open( "sample.dat", O_RDONLY );
  cout << "Another process[" << getpid( ) << "] has opened fd = " << fd <<endl;

  char buffer[4];
  buffer[3] = 0;

  // fork( );
  while ( read( fd, buffer, 3 ) != 0 ) {
    cout << "Another process[" << getpid( ) << "] has read " << buffer << endl;
    sleep( 1 );
  }

  close( fd );
}
