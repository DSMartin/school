// CSS430: Effect of Caching/Paging

#include <sys/time.h>
#include <iostream>

#define SIZE 400                        // Matrix size

using namespace std;

int a[SIZE][SIZE];                      // c = a * b
int b[SIZE][SIZE];
int c[SIZE][SIZE];

int main( ) {
  struct timeval startTime, endTime;

  // Normal -------------------------------------------------------------------
  gettimeofday( &startTime, NULL );      // start timer

  // Initialization
  for ( int i = 0; i < SIZE; i++ )       // initialize a[][]
    for ( int j = 0; j < SIZE; j++ )
      a[i][j] = i + j;
  for ( int i = 0; i < SIZE; i++ )       // initialize b[][]
    for ( int j = 0; j < SIZE; j++ )
      b[i][j] = i - j;
  for ( int i = 0; i < SIZE; i++ )
    for ( int j = 0; j < SIZE; j++ )     // initialize c[][]
      c[i][j] = 0;

  // Matrix Multiplication
  for ( int i = 0; i < SIZE; i++ )
    for ( int j = 0; j < SIZE; j++ )
      for ( int k = 0; k < SIZE; k++ )
	c[i][j] += a[i][k] * b[k][j];

  gettimeofday( &endTime, NULL );         // stop timer

  cout << "Answer example c[" << SIZE/2 - 2 << "][" << SIZE/2 - 2<< "] = "
       << c[SIZE/2 - 2][SIZE/2 - 2] << endl;
  cout << "Normal version: time = " 
       << ( endTime.tv_sec * 1000000 + endTime.tv_usec )
    - ( startTime.tv_sec * 1000000 + startTime.tv_usec ) << endl;

  // Flip the matrix B diagonal -----------------------------------------------
  gettimeofday( &startTime, NULL );       // start timer

  // Initialization
  for ( int i = 0; i < SIZE; i++ )        // initialize a[][]
    for ( int j = 0; j < SIZE; j++ )
      a[i][j] = i + j;
  for ( int i = 0; i < SIZE; i++ )        // initialize b[][]
    for ( int j = 0; j < SIZE; j++ )
      b[j][i] = i - j;                    // i and j are flipped.
  for ( int i = 0; i < SIZE; i++ )        // initialize c[][]
    for ( int j = 0; j < SIZE; j++ )
      c[i][j] = 0;

  // Matrix Multiplication
  for ( int i = 0; i < SIZE; i++ )
    for ( int j = 0; j < SIZE; j++ )
      for ( int k = 0; k < SIZE; k++ )
	c[i][j] += a[i][k] * b[j][k];    

  gettimeofday( &endTime, NULL );          // stop timer

  cout << "Answer example c[" << SIZE/2 - 2 << "][" << SIZE/2 - 2<< "] = "
       << c[SIZE/2 - 2][SIZE/2 - 2] << endl;
  cout << "Flipping Version: time = " 
       << ( endTime.tv_sec * 1000000 + endTime.tv_usec )
    - ( startTime.tv_sec * 1000000 + startTime.tv_usec ) << endl;
}
