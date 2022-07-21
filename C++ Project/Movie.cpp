/*
By Robin Rai
V.1.0.0
Created on 05/03/2020
This file features the Movie constructors, some overloaded operators, and a test function
*/

#include <string>
#include "Movie.h"
#include <vector>
#include <sstream>

using namespace std;    //I do not want to type std eight-hundred times

Movie::Movie(string title, int date, string ageRating, string genre, int duration, int usrRating) {
	this->title = title;
	this->year = date;
	this->ageRating = ageRating;
	this->duration = duration;
	this->usrRating = usrRating;
	this->genre = genre;

};

Movie::Movie() {
	this->title = "INVALID";
	this->ageRating = "INVALID";
	this->genre = "INVALID";
	this->year = 0000;
	this->duration = 0000;
	this->usrRating = 0000;
};

//big and chunky so not inline/in header file.
std::istream &operator>>(std::istream &inputStream, Movie &mov) {
	//you silly goose no const, it's literal only purpose is to edit mov
	string title, ageRating, genre;
	int year, duration, usrRating;

	char q; //empty char that represents where a quotation mark would be
	char c; //empty char that represents where a comma would be

	//if input is in the perfect layout, delimited by "s,
	if (inputStream
				>> q && getline(inputStream, title, '"')
				// " + title
				>> c >> year >> c
				// , year ,
				>> q && getline(inputStream, ageRating, '"') >> c
				// " + ageRating ,
				>> q && getline(inputStream, genre, '"')
				// " + genre
				>> c >> duration >> c
				// , duration ,
				>> usrRating)
		// usrRating

	{
		//set mov's parameters accordingly
		mov = Movie(title, year, ageRating, genre, duration, usrRating);
	} else {
		//otherwise fail with the flag, and just make a default Movie.
		inputStream.clear(ios_base::failbit);
		mov = Movie();
	}
	return inputStream;
}

//these aren't in the header file/inlined because they're quite chunky and have quite a few function calls.
bool operator<(const Movie &mov1, const Movie &mov2) {
	//logic for o1 < o2
	if (mov1.year == mov2.year) {
		//if the years are the same
		if (mov1.title.compare(mov2.title) == 0) {
			//if mov1's title is equal to mov2's
			return false;
		}
		if (mov1.title.compare(mov2.title) > 0) {
			//if mov1's title is bigger than mov2's
			return true;
		}
		//if mov1's title is smaller
		return false;
	} else {
		//man IDE's are advanced. If the years aren't the same return the right result.
		return mov1.year < mov2.year;
	}
}

bool operator>(const Movie &mov1, const Movie &mov2) {
	//logic for mov1 > mov2. Same as above so no comments
	if (mov1.year == mov2.year) {
		if (mov1.title.compare(mov2.title) == 0) {
			return false;
		}
		if (mov1.title.compare(mov2.title) < 0) {
			return true;
		}
		return false;
	} else {
		return mov1.year > mov2.year;
	}
}

bool operator<=(const Movie &mov1, const Movie &mov2) {
	//anything that's not mov1 > mov2
	if (mov1 > mov2) {
		return false;
	}
	return true;
}


bool operator>=(const Movie &mov1, const Movie &mov2) {
	//anything that's not mov1 < mov2
	if (mov1 < mov2) {
		return false;
	}
	return true;
}

void movieTest() {
	//string title, int date, string ageRating, string genre, int duration, int usrRating
	Movie test1("Test1", 2001, "PG", "Film-Noir", 144, 9.0);
	Movie test2("Test2", 2000, "PG", "Film-Noir", 144, 9.0);
	Movie test3("Test3", 2000, "PG", "Film-Noir", 144, 9.0);

	vector<Movie> test;
	test.push_back(test1);
	test.push_back(test2);
	test.push_back(test3);


	Movie test4;

	//"Seven Samurai",1954,"UNRATED","Action/Adventure/Drama",207,0
	string line = "\"Test4\",2000,\"PG\",\"Film-Noir\",144,9.0";
	std::istringstream iss(line);
	iss >> test4;

	test.push_back(test4);


	Movie test5;
	line = "999,\"Film-Noir\",pee is stored in the balls,9.0";
	std::istringstream iss2(line);
	iss2 >> test5;

	test.push_back(test5);
	Movie test6;
	test.push_back(test6);
	cout << "Movie.cpp test: " << endl;
	for (int i = 0; i < test.size(); i++) {
		cout << "Movie " << i + 1 << ": " <<
			 endl;
		cout << test[i];
	}


	cout << "Movie 1 compared to Movie 2: " << endl;

	bool result1, result2, result3, result4, result5, result6;
	result1 = test1 < test2;
	result2 = test1 > test2;
	result3 = test1 == test2;
	result4 = test1 <= test2;
	result5 = test1 >= test2;
	result6 = test1 != test2;
	cout << "< " << result1 << endl;
	cout << "> " << result2 << endl;
	cout << "== " << result3 << endl;
	cout << "<= " << result4 << endl;
	cout << ">= " << result5 << endl;
	cout << "!= " << result6 << endl;


	cout << "Movie 2 compared to Movie 3: " << endl;


	result1 = test2 < test3;
	result2 = test2 > test3;
	result3 = test2 == test3;
	result4 = test2 <= test3;
	result5 = test2 >= test3;
	result6 = test2 != test3;
	cout << "< " << result1 << endl;
	cout << "> " << result2 << endl;
	cout << "== " << result3 << endl;
	cout << "<= " << result4 << endl;
	cout << ">= " << result5 << endl;
	cout << "!= " << result6 << endl;

	cout << "Movie 5 compared to Movie 6: " << endl;


	result1 = test5 < test6;
	result2 = test5 > test6;
	result3 = test5 == test6;
	result4 = test5 <= test6;
	result5 = test5 >= test6;
	result6 = test5 != test6;
	cout << "< " << result1 << endl;
	cout << "> " << result2 << endl;
	cout << "== " << result3 << endl;
	cout << "<= " << result4 << endl;
	cout << ">= " << result5 << endl;
	cout << "!= " << result6 << endl;

}
