# SocialSelf
## The Pitch
Imagine you're at a party - and you meet this hot chick/dude. You hit it off, and after the party is over,
you decide to add each other on your social media accounts to stay in touch. So you ask them for their facebook name.
And their phone number. And their instagram. And their twitter. And their snapchat... And so on...

Or, say it's your first day at your new job, and you've just met your new coworkers! Better get to know them! Let's add them on facebook! And instagram... And twitter...

You get the picture. When did meeting new people become such a pain in the ass?

That's where the concept of SocialSelf comes in - what if you could add someone across all their
social media accounts with the simple scan of a QR code?

## The details
SocialSelf was originally created at QHacks2016 by me, Harry Liu, Marco Ly, and Mustafa Ismail, as an iOS and web app (http://devpost.com/software/social-self). This repository is 
my remake as a standalone android app in Android and php/mySQL.

We were inspired mainly by snapchat's snapcodes, which allowed you to add users by scanning their snapcode. We thought hey, why not make an app that lets you add a user on ALL their social media, 
instead of just snapchat?

## Implementation
SocialSelf was written in Android and php/mySQL. The app communicates with the server by sending simple POST requests, and recieving a JSON 
string. I used the zBar scanner library (https://github.com/dm77/barcodescanner) to scan the QR codes, and google's chart API to generate them.
The app uses facebook API to authenticate users, and displays their various social media accounts as webview fragments inside a tabbed layout in the app.

## What it does right now
Create an account/log in with facebook authentication (no passwords! hooray!)

Add your facebook, instagram, twitter, and linkedin accounts via your usernames for those accounts, as well as your email and phone number

Create your own, unique QR code based on your facebook id.

Scan someone else's QR code, and view all their shared social media pages (via webview)

Edit the account information you provide.

## TODO:
Here's a list of things I still need to implement, but at the moment, this project has been put on hold indefinitely. Feel free to fork it, and work on it yourself!

Better data validation (validating email, phone number, account names, etc.)

Better memory management. 

Better activity lifecycles.

Refactoring, optimizing, modularizing current code.

Optimizing webview.

Moving away from webview and into API integration.

Quality assurance and testing

Adding compatibility with more social media accounts (snapchat, github, etc.)

Adding in more features - for example, multiple QR codes/setups per user, etc.

Redesign UI/UX
