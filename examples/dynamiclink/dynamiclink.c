#include <stdio.h>
#include <dlfcn.h>
#define MAXCHAR 100

int main( ) {
  char libraryName[MAXCHAR];
  void *stub; // an opaque handle
  char functionName[MAXCHAR];
  void (* func)( ); // a loaded function

  for ( ; ; ) {

    printf( "library name = " );
    scanf( "%s", libraryName );
    if ( ( stub = dlopen( libraryName, RTLD_LAZY ) ) == NULL ) {
      fprintf( stderr, "library: %s not found\n", libraryName );
      continue;
    }

    printf( "function name = " );
    scanf( "%s", functionName );
    if ( ( func = dlsym( stub, functionName ) ) == NULL ) {
      fprintf( stderr, "function: % not found\n", functionName );
      continue;
    }
    ( *func )( );
    dlclose( stub );  
  }
}
