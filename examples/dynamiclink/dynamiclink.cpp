#include <iostream>
#include <string>
#include <dlfcn.h>
using namespace std;

int main( ) {
  while ( true ) {
    string libraryName;
    cout << "library name = ";
    cin >> libraryName;
    // cout << libraryName.c_str( ) << endl;
    void* stub; // an opaque handle
    if ( ( stub = dlopen( libraryName.c_str( ), RTLD_LAZY ) ) == NULL ) {
      cerr << "library: " << libraryName << " not found" << endl;
      continue;
    }

    string functionName;
    cout << "function name = ";
    cin >> functionName;
    void (* func)( ); // a loaded function
    if ( ( (void *)func = dlsym( stub, functionName.c_str( ) ) ) == NULL ) {
      cerr << "function: " << functionName << " not found" << endl;
      continue;
    }
    (* func)( ); 
    dlclose( stub );  
  }
}
