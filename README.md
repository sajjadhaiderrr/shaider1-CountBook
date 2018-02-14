# CountBook
This program is written to meet the requirement of Assignment 1 for the course CMPUT 301 in Fall 2017 at the University of Alberta.

# Problem Description from the assignment page
Consider the situation of someone who needs to count things. Perhaps those things are supplies being consumed like food or medicine, or people entering some event. What is needed is a simple, attractive, intuitive, mobile app to manage a set of counters. Let us call this app: CountBook.

Specifically, each counter has a record with the following fields:

* name (textual)
* date (when the counter was made or the current value was last changed, in yyyy-mm-dd format)
* current value (non-negative numeric)
* initial value (non-negative numeric)
* comment (textual)
* Only the comment field might be left blank for a counter. The user needs to specify the name and initial value.

The app should allow the user to:

* show a list of counters, along with a summary of the number of counters
* add a new counter (which always appends to the bottom end of the list)
* increment a counter's current value by one
* decrement a counter's current value by one
* reset a counter's current value to its initial value
* view the details of an existing counter
* edit the fields of a counter directly except the date
* delete an existing counter
