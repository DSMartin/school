#include <stdlib.h>    // atoi
#include <sys/types.h> // fork, wait, getpid
#include <unistd.h>    // fork, getpid
#include <sys/wait.h>  // wait
#include <iostream>    // cout
#include <sys/time.h>  // gettimeofday

using namespace std;

void compute( double array[], int size, int iteration ) {
  cout << getpid( ) << endl;
  for ( int i = 0; i < iteration; i++ )
    for ( int j = 0; j < size; j++ )
      array[j] = i * j;
  exit( 0 );
}

int main( int arc, char* argv[] ) {
  // validate argumenets
  if ( arc != 4 ) {
    cout << "usage: ./multiprocess #processes arraySize iteration" << endl;
    exit( -1 );
  }
  int nProcs = atoi( argv[1] );
  int size = atoi( argv[2] );
  int iteration = atoi( argv[3] );
  cout << "nProcs = " << nProcs << ", size = " << size << ", iteration = " 
       << iteration << endl;

  double array[size];
  struct timeval startTime;
  gettimeofday( &startTime, NULL );

  // fork child processes
  for ( int i = 0; i < nProcs; i++ ) {
    if ( fork( ) == 0 ) {
      compute( array, size, iteration );
    }
  }
  
  // wait for child processes
  for ( int i = 0; i < nProcs; i++ ) 
    wait( 0 );

  struct timeval endTime;
  gettimeofday( &endTime, NULL );

  long time = ( endTime.tv_sec - startTime.tv_sec ) * 1000000 
    + ( endTime.tv_usec - startTime.tv_usec );
  cout << "elapse time = " << time << " usec" << endl;
}
