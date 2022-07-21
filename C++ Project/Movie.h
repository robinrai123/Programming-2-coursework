/*
By Robin Rai
V.1.0.0
Created on 05/03/2020
This file features the object declaration, all the headers, getters, and some overloaded operators
*/

#ifndef PROJECT_MOVIE_H
#define PROJECT_MOVIE_H

#include <string>
#include <iostream>

using namespace std;    //gav said it's okay to use this!

class Movie {
private:

	string title;
	string ageRating;
	//enum ageRating {PG13, APPROVED, R, PG, NOTRATED, G};
	//i'm not using enums as the ageRating string overloaded into an enum value would require perfect string input anyway.
	string genre;
	int year;
	int duration;
	float usrRating;


public:
	Movie(string title, int date, string ageRating, string filmGenre, int duration, int usrRating);

	Movie();

	~Movie() {
		//cout << title << " Movie destroyed" << endl;
	};

	inline std::string getTitle() const {
		return this->title;
	}

	inline int getYear() const {
		return this->year;
	}

	inline string getAgeRating() const {
		return this->ageRating;
	}

	inline string getGenre() const {
		return this->genre;
	}

	inline int getDuration() const {
		return this->duration;
	}

	inline float getUsrRating() const {
		return this->usrRating;
	}

	friend inline ostream &operator<<(ostream &outputStream, const Movie &mov);

	friend istream &operator>>(istream &inputStream, Movie &mov);

	friend bool operator<(const Movie &mov1, const Movie &mov2);

	friend bool operator>(const Movie &mov1, const Movie &mov2);

	friend bool operator<=(const Movie &mov1, const Movie &mov2);

	friend bool operator>=(const Movie &mov1, const Movie &mov2);

	friend inline bool operator==(const Movie &mov1, const Movie &mov2);

	friend inline bool operator!=(const Movie &mov1, const Movie &mov2);
};

//lightweight so in headerfile and inlined
inline ostream &operator<<(ostream &outputStream, const Movie &mov) {
	//outputs a movie's data nicely when << operator is called on it
	outputStream << "\"" << mov.title << "\"," << mov.year << ",\"" << mov.ageRating << "\",\""
				 << mov.genre
				 << "\"," << mov.duration << "," << mov.usrRating << endl;
	return outputStream;
}

//same here for the inlining/headering
inline bool operator==(const Movie &mov1, const Movie &mov2) {
	//i'm not sure comparing by title also would be a great idea
	return mov1.year == mov2.year;
}

inline bool operator!=(const Movie &mov1, const Movie &mov2) {
	return mov1.year != mov2.year;
}

void movieTest();

//gotta be in header file to be inlined, gotta be friended to have access to variables
#endif //PROJECT_MOVIE_H
