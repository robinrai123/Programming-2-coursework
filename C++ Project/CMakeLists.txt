cmake_minimum_required(VERSION 3.15)
project(Project)

set(CMAKE_CXX_STANDARD 20)

add_executable(Project main.cpp Movie.cpp Movie.h MovieDatabase.cpp MovieDatabase.h)