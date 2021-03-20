# Design Choices and Reasoning

### Traveler.java, City.java, & Route.java

These three files comprise the functionality of The Traveling Husky. 

`City.java` is an object which represents a location. Each City contains four fields.
- `name` is a String representing the name of the location, like "Suzallo" or "Yosemite (CA)".
- `latitude` is a double representing the location's latitude.
- `longitude` is a double representing the location's longitude.
- `id` is an integer representing the 'index' of each city as it is initialized. This exists so each city's distance can be 
looked up in the distance matrix based off its id. The id starts at zero and increments for each city, i.e. if there were 20
cities they'd be ID'd 0 through 19. This makes it incredibly easy to find each city in the distance matrix because each ID 
corresponds to its index position in the array. (More on the distance matrix later in this document.)

`Route.java` is an object that represents a route comprised of all the cities in the location set. A route stores two properties.
- `route` is an array of `City` objects representing the route in order. It is an array because the number of `City` objects is
always fixed.
- `distance` is a double representing the total distance traveled by the route in kilometers. It is a double because distances must 
be precise, especially for locations that are close together like around UW.

`Traveler.java` holds all algorithms used to solve or approximate the traveling salesman problem. 
Constructing a `Traveler` object with a location set allows you to call any algorithm.

Inside the `Traveler` object are three fields.
- `cities` is an array of `City` objects that holds all the locations for the given location set. This is an array
as opposed to a List or Set because the size of the number of cities at any point in the traveling salesman problem is fixed.
Arrays also have O(1) retrieval time for a given element in the array, which makes traversal and rearrangement of the array simple.
- `matrix` is a two dimensional `double` array. This matrix represents a distance matrix, where each row and column represents a
`City`. Therefore, each element in a given row and column represents the distance from the `City` located in the row from the
`City` located in the column. The matrix array makes looking up the distance between two `City` objects an O(1)
operation, since each `City` can be looked up in the matrix based off its id. Therefore, `distance()` only has to be called once, when the `Traveler` is constructed, and the distances are stored there once and for all.
- `iterations` is an integer that is incremented in each algorithm when a new iteration of the method or algorithm is made. This
differs for different methods. For example, for the brute force method, the number of iterations is random: based on the input,
our branch-and-bound optimization can result in less or more recursive iterations. However, for other methods, like top-down,
left-to-right, and random, the number of iterations/operations is fixed: sorting is always Nlog(N) operations, and shuffling the
array is always N operations. `iterations` is then used to calculate the optimization of each algorithm compared to the algorithm's
expected runtime: e.g. brute-force is expected to run in factorial time, but the optimization makes it run in less.

### Main.java

`Main.java` allows a user to use the program. It parses the user's location set (a file located in `locations`) and turns it
into an array of `City` objects. Then, it constructs a `Traveler` to run algorithms on. Beyond this, it has user-friendly
print statements that tell the user how to use the program, abstracting away the details of how each algorithm is run or how
`Main.java` parses the CSV file.

`Main.java` also has its own print method which is implemented to make each character print one by one (like Pokémon or a video
game) because having large blocks of Strings print all at once is unreadable and not as fun :). However, if the printing is taking
too long, the user can simply hit `enter` and the entire String will print (like a skippable cutscene). This was implemented
using `Thread.sleep()` and `System.in.available()`.
