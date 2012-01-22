CC = g++
INCLUDES = -include /usr/include/errno.h
FLAGS = -DDEBUG

all:
	$(CC) $(FLAGS) $(INCLUDES) -o shell -lreadline commandparser.cpp shell.cpp