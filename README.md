***************************************************************************
Itenary
***************************************************************************
 
Proposed level of achievement: Project Gemini

Project scope:
Itenary is an android application that allows users to plan, store and view their travel itinerary. Trip details are stored in a
detailed format, providing offline access to trip information, as well as supporting editingof itinerary on the go. Users are allowed
to collaborate with other users to plan the same trip together.

User stories:
1. As a frequent traveller, i always find it troublesome to plan my own trips and succumb to online itineraries found on blogs
and websites.
2. As someone who love travelling with friends, it is difficult for us to plan for the trip together at the same time without meeting up.
3. As a user of synchronization services such as Google Docs, I find it hectic to type out a template for the trip that i am planning.

Features:
1. Signup/Login for users.
2. Add trips (E.g. China trip or Australia Trip).
3. Under each trip, allow users to add programs (E.g. dinner, sightseeing).
4. Allow programs to be created, read, updated and deleted.
5. Allow trips to be created, read and deleted.
6. Role based user permission: Users can invite users to collaborate (option of allowing them to view or to edit or both)
on the same trip.
7. Every user have their own “database”. One user is not allowed to view the content of another user unless
they are invited into their trips.

Bugs:
1. Users are allowed to delete a trip even if edit permission is not granted.

User testing:
Cognitive walkthrough
Clarification: Trips are the main object of this application. Examples of trips are (Vietnam with friends), (China with family), (Backpacking at Australia).
Within trips are many programs. Examples of programs are (Eating at Ding Tai Fung), (Flight to Melbourne), (Check in Airbnb).
We created 2 accounts for each of the 2 evaluators.
We tested the creation of account, login of accounts with both correct and incorrect details, creation of accounts with invalid details (e.g. password length < 4).
Create a new trip entirely from scratch both with and without adding in a program.
Add a new program to an existing trip.
Edit an existing program and ensure that correct details are brought up (use firebase console as a reference).
Delete an existing program.
Delete an existing trip (both clicking the ‘confirm delete’ and ‘do not delete’ button).
With the 2nd account, try to access the trips of the first account (uninvited).
With the 1st account, invite the 2nd account to view a trip.
With the 1st account, invite the 2nd account to edit a trip.

Specifications:
Made using:
Android studio for android application
Firebase for online storage of data
Git as a source control


