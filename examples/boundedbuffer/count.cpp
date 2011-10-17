#include <iostream>

void main( ) {
  int prev = -1;
  int curr = -1;
  
  while ( true ) {
    if ( curr % 1000 == 0 )
      cout << curr << endl;
    prev = curr;
    cin >> curr;
    if ( prev == -1 || prev + 1 == curr )
      continue;
    break;
  }
  cout << "ERROR!!! prev == " << prev << " curr == " << curr << endl;
}
