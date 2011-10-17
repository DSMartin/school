#include <iostream>

void func1( ) { // func1__Fv
  cout << "\t this is lib3's func1 with no arguments !!" << endl;
}

void func1( int a ) { // func1__Fi
  cout << "\t this is lib3's func1 with an integer !!" << endl;
}

void func2( ) { // func2__Fv
  cout << "\t this is lib3's func2 with no arguments !!" << endl; 
}

void func2( int a, double b ) { // func2__Fid
  cout << "\t this is lib3's func2 with an integer and a double !!" << endl;
}
