// for semaphore
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
// for shared region
#include <sys/shm.h>

// for fork
#include <unistd.h>

// for I/O
#include <stdio.h>
#include <iostream>
using namespace std;

const int nProcs = 3;   // the number of processes including the main
const key_t key = 75;   // the key associated with this semaphore/region
const int nsems = 1;    // the number of semaphore
const int semNum = 0;   // the semaphore number
int semId = -1;
int shmId = -1;
int *shmInt = NULL;

void P( ) {
  int semNum = 0;
  struct sembuf sops[nsems];
  sops[semNum].sem_num = semNum;
  sops[semNum].sem_op = -1;         // decrement the semaphore
  sops[semNum].sem_flg = SEM_UNDO;  // make it undoable

  semNum = semop( semId, sops, nsems );
  
  // cerr << "After P: semaphore=" << semNum << endl;
}

void V( ) {
  int semNum = 0;
  struct sembuf sops[nsems];

  sops[semNum].sem_num = semNum;
  sops[semNum].sem_op = 1;          // increment the semaphore
  sops[semNum].sem_flg = SEM_UNDO;  // make it undoable

  semNum = semctl( semId, semNum, GETVAL, 0 );
  // cerr << "Before V: semaphore=" << semNum << endl;
  semop( semId, sops, nsems );
}

int main( ) {
  int pid;

  cerr << "Main : " << ( pid = getpid( ) ) << endl;

  // get a semaphore and initialize it to 1
  if ( ( semId = semget( key, nsems, 0777 | IPC_CREAT ) ) == -1 ) {
    perror( "semget" );
    return -1;
  }
  semctl( semId, semNum, SETVAL, 1 );

  // get a shared region
  if ( ( shmId = shmget( key, 100, 0777 | IPC_CREAT ) ) == -1 ) {
    perror( "shmget" );
    return -2;
  }
  if ( ( shmInt = (int *)shmat( shmId, NULL, 0 ) ) == NULL ) {
    perror( "shmat" );
    return -3;
  }
  // initialize the shared integer
  *shmInt = 0;

  // fork processes
  for ( int i = 1; i < nProcs; i++ ) {
    int child;
    if ( ( child = fork( ) ) == 0 ) {
      cerr << "Child: " << ( pid = getpid( ) ) << endl;
      break;
    }
  }
  sleep( 2 );    // wait for all children to print out their PID

  // get in and out of a critical section
  while ( true ) {
    P( );

    int myOwnInt = *shmInt;  // read the shared integer

    cerr << "Process[" << pid << "] got in a critical section:" 
         << " shmInt = "; 
    cout << myOwnInt << endl;

    *shmInt = ++myOwnInt;    // write back the shared integer

    V( );
  }
  return 0;
}
