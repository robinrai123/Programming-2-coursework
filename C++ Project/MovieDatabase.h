/*
By Robin Rai
V.1.0.0
Created on 05/03/2020
 This file features the declaration for MovieDatabase, some getters, and headers
*/

#include <vector>
#include <algorithm>
#include "Movie.h"

#ifndef PROJECT_MOVIEDATABASE_H
#define PROJECT_MOVIEDATABASE_H


using namespace std;

class MovieDatabase {
private:
	vector<Movie> omdb;
public:
	MovieDatabase();

	MovieDatabase(const MovieDatabase &old) {
		for (int i = 0; i < old.omdb.size(); i++) {
			this->omdb.push_back(old.omdb[i]);
		}
	}

	~MovieDatabase() {
		//cout << "MovieDatabase destroyed" << endl;
	}

	MovieDatabase(const string &fileLocation);

	void sortFilms(int direction);

	void sortDuration();

	void sortTitleLength();

	vector<Movie> filterGenre(string genre) const;

	vector<Movie> filterAgeRating(string age) const;

	inline Movie getMovie(int index) const {
		return omdb[index];
	}

	inline void add(Movie &movie) {
		omdb.push_back(movie);
	}

	inline void add(vector<Movie> &db) {
		for (int i = 0; i < db.size(); i++) {
			this->omdb.push_back(db[i]);
		}
	}

	inline int size() const {
		return omdb.size();
	}

	friend std::ostream &operator<<(std::ostream &outputStream, const MovieDatabase &omdb);

};

void movieDatabaseTest();

#endif //PROJECT_MOVIEDATABASE_H
