#include <stdlib.h>    // atoi
#include <sys/types.h> // fork, wait, getpid
#include <unistd.h>    // fork, getpid
#include <sys/wait.h>  // wait
#include <iostream>    // cout
#include <sys/time.h>  // gettimeofday

struct paramType {
  double *array;
  int size;
  int iteration;
};

using namespace std;

void *compute( void *param ) {
  double *array; // initialize
  int size; // initialize
  int iteration; // initialize
  for ( int i = 0; i < iteration; i++ )
    for ( int j = 0; j < size; j++ )
      array[j] = i * j;
}

int main( int arc, char* argv[] ) {
  // validate argumenets
  if ( arc != 4 ) {
    cout << "usage: ./multiprocess #threads arraySize iteration" << endl;
    exit( -1 );
  }
  int nThreads = atoi( argv[1] );
  int size = atoi( argv[2] );
  int iteration = atoi( argv[3] );
  cout << "nThreads = " << nThreads << ", size = " << size << ", iteration = " 
       << iteration << endl;

  double array[nThreads][size];

  // parameter assignments
  struct paramType param[nThreads];
  for ( int i = 0; i < nThreads; i++ ) {
    param[i].array = array[i];
    param[i].size = size;
    param[i].iteration = iteration;
  }

  struct timeval startTime;
  gettimeofday( &startTime, NULL );

  pthread_t child[nThreads];

  // fork child threads
  for ( int i = 0; i < nThreads; i++ ) {
    // use pthread_create
  }
  
  // wait for child thread
  for ( int i = 0; i < nThreads; i++ ) 
    // use pthread_join

  struct timeval endTime;
  gettimeofday( &endTime, NULL );

  long time = ( endTime.tv_sec - startTime.tv_sec ) * 1000000 
    + ( endTime.tv_usec - startTime.tv_usec );
  cout << "elapse time = " << time << " usec" << endl;
}
