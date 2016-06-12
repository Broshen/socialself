# SocialSelf
## The Pitch
Imagine you're at a party - and you meet this hot chick/dude. You hit it off, and after the party is over,
you decide to add each other on your social media accounts to stay in touch. So you ask them for their facebook name.
And their phone number. And their instagram. And their twitter. And their snapchat... And so on...

Or, say it's your first day at your new job, and you've just met your new coworkers! Better get to know them! Let's add them on facebook! And instagram... And twitter...

You get the picture. When did meeting new people become such a pain in the ass?

That's where the concept of SocialSelf (lol the name is a work in progress) comes in - what if you could add someone across all their
social media accounts with the simple scan of a QR code?

## The details
SocialSelf was originally created at QHacks2016 by me, Harry Liu, Marco Ly, and Mustafa Ismail, as an iOS and web app (http://devpost.com/software/social-self). This repository is 
my remake as a standalone android app as an exercise in Android and php/mySQL.

We were inspired mainly by snapchat's snapcodes, which allowed you to add users by scanning their snapcode. We thought hey, why not make an app that lets you add a user on ALL their social media, 
instead of just snapchat?

## Implementation
SocialSelf was written in Android and php/mySQL. The app communicates with the server by sending simple POST requests, and recieving a JSON 
string. I used the zBar scanner library (https://github.com/dm77/barcodescanner) to scan the QR codes, and google's chart API to generate them.
The app uses facebook API to authenticate users, and displays their various social media accounts as webview fragments inside a tabbed layout in the app.

## What it does right now
This project is by no means complete. There's still a huge semi completed list of things I still need to implement for this to 
become a truly usable app, but here's what you can do right now:

Create an account/log in with facebook authentication (no passwords! hooray!)

Add your facebook, instagram, twitter, and linkedin accounts via your usernames for those accounts, as well as your email and phone number

Create your own, unique QR code based on your facebook id.

Scan someone else's QR code, and view all their shared social media pages (via webview)

Edit the account information you provide.

## TODO:
Here's a list of things I still need to implement, but at the moment, this project has been put on hold indefinitely. Feel free to fork it, and work on it yourself!

Better data validation (is your phone number REALLY 123456789? is your facebook username REALLY xzonxoifnasd?, etc.)

Better memory management. I'm pretty sure I have a memory leak somewhere in there - at one point it was using upwards of 200MB of RAM (lol)

Better activity lifecycles.

Refactoring and optimizing current code - There are some areas where I can definitely modularize and reuse my code instead of copy/pasting it and changing 1-2 things

Optimizing webview - one thing I haven't been able to figure out is how to keep the webview loaded offscreen. Currently, the web pages reload everytime you enter, which is kinda slow

Moving away from webview - working directly with the social media API so that users can literally add each other with a click of a button

More extensive testing/test cases - I haven't tested this outside of whether it can work or not, partly because I only have 1 phone, partly because no one likes testing.

Security (nonexistent) - I should really move away from mySQLi_query and use prepared statements...

Adding compatibility with more social media accounts. I wanted snapchat to be in here, but they don't have a webapp nor an official api so... bah. Also considered pinterest, and github.

Adding in more features - for example, multiple QR codes/setups per user, etc.

Redesign UI/UX (it's pretty ugly right now...)
