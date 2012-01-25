// 
//  commandparser.h
//  shell
//  
//  Created by Hildebrant, John on 2012-01-21.
// 

#ifndef COMMNADPARSER_H
#define COMMNADPARSER_H
#include <string>
#include <vector>
using namespace std;

/**
 * This struct contains the detail part of command
 * Each part is a command with extra information to determine whether
 * it should run in the background or pipe the output to next program
 */
struct CommandParserResultPart
{
	string command;
	vector<string> arguments;
	bool background;
	bool pipe_with_next;
	string redirIn;
	string redirOut;
	string redirAppend;
	
	CommandParserResultPart()
	{
		background = false;
		pipe_with_next = false;
		redirIn = "";
		redirOut = "";
		redirAppend = "";
	}
	
	string getAbsolutePath(string searchPath);	
};

/**
 * The result of the command parser
 * It may contains many command parts.
 */
struct CommandParserResult
{
	vector<CommandParserResultPart> parts;
};

/**
 * Class providing the command parsing ability
 */
class CommandParser
{
	public:
	static CommandParserResult parse(string command);
};
#endif