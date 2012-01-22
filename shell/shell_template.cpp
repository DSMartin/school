#include <iostream>
#include <vector>
#include <stack>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#define MAX 256
#define STDIN 0
#define STDOUT 1

using namespace std;

void extractCommand( char buffer[], int top, int cur, char delim,
		     vector<char**> &commands, vector<char> &delims ) {
  // check if there is a command between top and cur
  if ( top == -1 || top == cur )
    return;

  // prepare a space for this command
  char *command = new char[MAX];     
  bzero( command, MAX );
  
  // copy the command from buffer
  bool isDelim = ( delim == ';' || delim == '&' || delim == '|' );
  if ( isDelim )
    strncpy( command, buffer + top, cur - top );     // remove the delimitor
  else
    strncpy( command, buffer + top, cur - top + 1 ); // include the last char
  
  // tokenize all argunents of the command string.
  char *sep = " \t";   // tokens
  char **arguments = new char*[MAX];
  int index = 0;
  for ( char *arg = strtok( command, sep ); arg != NULL; arg = strtok( NULL, sep ) ) {
    arguments[index++] = arg;
  }
  arguments[index] = NULL;
  
  // register command/arguments to the command list
  commands.push_back( arguments );
  if ( isDelim )
    delims.push_back( delim );
  else
    delims.push_back( ';' );
}

int main( int argc, char *argv[] ) {
  
  char buffer[MAX];              // a line buffer

  // keep reading line by line
  while ( cin ) {
    // print out a command prompt
    cout << "% ";

    // reinitialize and read a command line
    bzero( buffer, MAX );
    cin.getline( buffer, MAX );

    // delimit each command with |, ;, and & 
    vector<char**> commands;  // a list of commands/arguments
    vector<char> delims;      // a list of delimitors such as | ; &
    int top = -1;             // the current command top in buffer
    for ( int i = 0; i < strlen( buffer ); i++ ) {
      // skip unnecessary spaces
      if ( top == -1 && ( buffer[i] == ' ' || buffer[i] == '\t' ) )
	continue;

      // set the next command top
      if ( top == -1 )
	top = i;

      // reach the end of the current command
      if ( i == strlen( buffer ) - 1 || 
	   buffer[i] == ';' || buffer[i] == '&' || buffer[i] == '|' ) {

	// extract current command from buffer
	extractCommand( buffer, top, i, buffer[i], commands, delims );
	top = -1;
      }
    }

    vector<char**>::const_iterator ci;
    // for debugging
    int i = 0;
    for ( ci = commands.begin( ); ci != commands.end( ); ci++ ) {
      char **arguments = *ci; 
      for ( int j = 0; arguments[j] != NULL; j++ )
	cout << "command[" << i << "][" << j << "]: " << arguments[j] << endl;
      i++;
    }

    vector<char>::const_iterator di;
    // for debugging
    i = 0;
    for ( di = delims.begin( ); di != delims.end( ); di++ )
      cout << "delimitor[" << i++ << "]: " << *di << endl;

    commands.clear( );
    delims.clear( );
  }
}

