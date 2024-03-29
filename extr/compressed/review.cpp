#include "review.h"
#include <iostream>

void ReadStdIn() {
    int iValue;
    double dValue;
    std::string sValue;

    // Read input values from stdin
    std::cin >> iValue >> dValue >> sValue;

    std::cout << iValue << std::endl;
    std::cout << dValue << std::endl;
    std::cout << sValue << std::endl;
}