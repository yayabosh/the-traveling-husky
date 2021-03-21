# The Traveling Husky

### An analysis of a difficult problem

The [traveling salesman problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem) is a famous problem in computer
science. It's an NP-hard problem, which means not only is it difficult to solve, but once solved, it's hard to even the solution is correct!
The problem asks:

**Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city exactly once and returns to the origin city?**

One of the easiest ways to think about solving this is using brute force: draw out every possible route combination between every 
city and then compare each route by distance. The route combination with the shortest distance traveled is the winner. However, this 
is easier said than done. Unfortunately, the brute force solution solves the traveling salesman problem in **factorial** time. 
That's O(N!) 😱! This runtime is unacceptable (unless you want to wait around for a long time), but the solution is guaranteed to 
be 100% accurate.

### What if we didn't care about 100% accuracy? 

After all, if you weregiven 200 cities and asked to find the shortest route between them,
being off by a few cities doesn't really matter *that* much. Plus, calculating the answer using brute force would take factorial time.
200 factorial is such a large number [Google can't even define it](https://www.google.com/search?q=200!&rlz=1C1CHBF_enUS852US852&oq=200!)!

Therefore, since we don't necessarily care about 100% accuracy, what if we defined some approximations to the traveling salesman
problem? By approximating the problem, we can define algorithms that might not give us the right answer every time, but can execute
much quicker than the brute force method. By creating or implementing other algorithms for the
traveling salesman problem, each algorithm's runtime, accuracy, and optimization can be analyzed to find an optimal balance between
time complexity and accuracy.

### How do these algorithms work?

The Traveling Husky is named after a Husky because the program is inspired by the many unique paths that can be taken to and from locations around the University of Washington (see `locations/uw.csv`). However, since the program uses latitude and longitude coordinates to compute distances, a user can enter any locations on Earth and this program will still work. Therefore, the `locations` folder has many other location sets containing points around the United States and Earth.

There are five algorithms to solve or approximate the traveling salesman problem in this repository. 
Two of them are existing algorithms (brute force and nearest neighbor) and three of them are self-created.

**💪 [Brute force](https://en.wikipedia.org/wiki/Travelling_salesman_problem#Exact_algorithms)**: The pure brute force method runs in O(N!) time, but is optimized using a branch-and-bound 
approach. The algorithm's worst case complexity is still O(N!) time, but it usually runs quicker. 
**Note: This algorithm is restricted to up to only 13 locations (cities) at once, because anything more takes a long time to compute.**
However, if you have the time, you can run it with however many locations you'd like in the `Traveler.java` file 😊.

**👀 [Nearest neighbor](https://en.wikipedia.org/wiki/Nearest_neighbor_search)**: This normally runs in O(N^2) time, but is optimized to run in less than O(N^2) by keeping track of already visited/compared cities. This algorithm chooses a city as a starting point, then repeatedly visits
the nearest city until every city has been visited. Even though this many not be a perfect approach, it is much more efficient
than the brute force algorithm's runtime, usually semi-accurate (depending on each city's location), and is quite intuitive.

**⬇️ Top down and ➡️ left to right (self-created)**: These two algorithms run in O(Nlog(N)) time due to sorting. Top down visits cities based on their
position on the "y-axis", i.e., visiting the city with the highest latitude first, then repeatedly visiting the city with the
next highest latitude until every city has been visited. Left to right does the same thing except with the x-axis, repeatedly
visiting each city with the next highest longitude. These are not perfect, but in situations where cities are not uniformly distributed
they can be very effective. Since these algorithms sort cities based on their latitudinal and longitudinal positions, it is not 
optimized beyond Nlog(N).

**😈 Random (personal favorite)**: Runs in O(N) time. The random algorithm simply returns a random route with all cities.
It is nearly never accurate; however, because of it's efficient runtime, it can be simulated hundreds of thousands of times to get
a better and better answer. It is the [Bogosort](https://en.wikipedia.org/wiki/Bogosort) of traveling salesman problem approximations.

### How do I use this?

- Clone or download this repository and open it in the text editor/IDE/application of your choice.
- To use the Traveling Husky, run it just as you would run any other Java file. Open `Main.java` and type `javac Main.java && java Main` into a terminal.
    - **Tip**: This program prints output to the terminal. For optimal viewing, expand your Terminal so you can see more of the program output. Type `clear` between different runs/compiles if you want to have an empty terminal each time.
    - **Tip 2**: This program prints each character to the output one by one like Pokémon!, instead of the entire string. 
      If you want to see the entire string print, press "enter" while the program is printing to auto-complete the rest of the string.
- The default file of locations to be used is a set of 10 locations around UW. You can see this CSV file, along with other location
  sets in the folder titled `locations`. If you want, you can add or remove locations from any file and run the program to see 
  how the route changes.
    - **Tip**: Instead of removing locations, you can "comment" them out by adding a # symbol before each line. These locations will be
      ignored by the program.
- You can also change the current location set at line 17 in `Main.java`. Change `locations/uw.csv` to `locations/YOURFILENAME.csv`.
  This allows you to simulate algorithms on different datasets, some much larger than others. (Your file doesn't have to be CSV,
  but since all the location sets are formatted and parsed based on commas, it makes more sense to use CSV.)
- To choose an algorithm, simply type the command next to each algorithm's name and let the output go! The route and its statistics
  will print to the console. Have fun trying out different locations and algorithms!
  - **Note**: The accuracy statistic compares the chosen algorithm's route with the brute force algorithm's route, i.e. the most accurate one.
  Since the brute force method can only handle up to 13 cities (otherwise it'd take too long for a user to wait), if your location set
  contains >13 cities, the accuracy statistic will not print.
