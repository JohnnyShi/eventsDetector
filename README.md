##Viagogo Coding Challenge by Yuan

Programming Language: Java 8

Deliverables: README.txt, Driver.java, Event.java, Ticket.java

Usage: Simply use terminal to 'javac Driver.java', then use 'java Driver'.

### Requirements

1. Randomly generate seed data
2. Operate in a world ranges within [-10, 10] for x and y axis
3. Assume each co-ordinate hold at most one event
4. Each event has a unique id
5. Each event has zero or more tickets
6. Each ticket has a non-zero price
7. Distance is calculated by Manhattan distance

Input: A type-in user location as a pair of coordinates
Return: A list of five closest events, along with the cheapest ticket price for each event

### Design Thoughts

1. Object Oriented Design:
  I use a class for event, containing event id, location, a ticket amount counter, a double variable for cheapest price, and a hashmap data structure to store ticket type and ticket object. And for the ticket object, it simply has a string to represents ticket type, an integer to show ticket amount, and a price for this type of ticket. I've given an example of 'VIP' tickets, with a random generator to give ticket amount as an integer between [200, 300], and a ticket price as a double between[900.00, 1100.00].

2. Logic of main function
  In the driver class, I firstly generates an int array board with size of 21*21. After that, generating a number no more than 20 as event amounts, then using conditional probability to distribute these events evenly in the board with only one swipe of board. The probability of any arbitrary combination of events place can be proved to be the same.
  Next, print out the board for better user experience, and parse a valid input location. Since in the board, the row index i and col index j lies in [0, 20], while in actual word they should be in [-10, 10], so I get the equation of coordinates to be x = j - (board[0].length-1) / 2; y = (board.length-1) / 2 - i.
  And for the final step, I use hashmap to store event object as key, and the distance as value, then applying breadth first search through a queue of coordinates and a boolean array to avoid revisiting, and finally get 5 nearest result. For the last step, I use a min heap to sort these 5 map entry and return this heap to main function, and poll one by one to get a top 5 list with ascending order. Here I assume for tie cases, like there are 7 places of the same distance, I will strictly output 5 places by recording the first 5 places I have been to.

3. Follow up questions:
  If there are multiple events in the same location, actually I have already implemented this, by using a locEventMap<Integer, List<Event>>. In fact if there is no more than 1 event then I would use a single event object as value, but to support multiple events in the same location, it's convenient to use a list, and add all the event to the top k list were it visited.

  And for a much bigger world, firstly the reason I choose BFS for the search is for the large scale consideration, because a recursive method can cause stack over flow when faced with too many levels of recursive calls. And another thing is you can use a approach like a indexing for each region. If the map is big and the events are relatively sparse, then I can firstly use a up-level search for a rough region, and then to detailed search within this region. Kind of like google map's zoom in and out function.

