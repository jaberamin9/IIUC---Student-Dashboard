#App URL: https://play.google.com/store/apps/details?id=com.blogspot.softlabsja.iiucstudentapp&hl=en&gl=US


<img src="images/img.jpg" width="600">

<a name="br1"></a> 

**IIUC - Student**

**Dashboard**

**App**



<a name="br2"></a> 

**Objective**

This app is built using the Android framework and primarily utilizes XML and Java languages. The main

function of this app is to convert the IIUC website (only student panel) into an Android app.

**Here's a detailed explanation of how the app works:**

First, we closely observe the functionality of the IIUC website, ensuring that we capture all the essential

features. Then, utilizing the **JSoup library**, we collect the necessary data and seamlessly integrate it into the

app through both GET and POST methods.

**Why is this app needed, you might wonder?**

Well, the IIUC website currently lacks responsiveness for mobile users, resulting in an unsatisfactory user

experience. Our app steps in to bridge this gap by enhancing the UI/UX specifically for mobile users, making it

more intuitive and user-friendly than the website itself. Furthermore, it's worth noting that there are currently no

mobile apps available for IIUC students on the Play Store, making our app a valuable addition to their digital

resources.

In summary, our app not only addresses the usability issues of the IIUC website but also fills a gap in the

availability of mobile applications tailored to the IIUC student community.



<a name="br3"></a> 

Android

Firebase

**XML:** Android XML is primarily for defining the

Why do we use Firebase as a database?

visual layout of your app's UI.

●

●

●

●

It is a real-time database.

**JAVA :** Java is used for writing the code that makes

the app function and respond to user actions.

To shut down the entire app.

To control the app version.

To keep the about page up-to-date.

These two components work together to create a

fully functional Android application.



<a name="br4"></a> 

**Functionality**

•

•

•

•

•

•

•

•

•

•

•

•

•

•

•

Notice

One time login

Course Registration

Course Add/Drop

Payment History

Result View

Registration Summary

Course Status

Ter

Profile

Change Password

Fee Calculator

Browser

PDF & IMG viewer

Download PDF & IMG



<a name="br5"></a> 

Login & Forget Password



<a name="br6"></a> 

**Webview**

Here, we showcased the entire website

using the built-in browser system using

a WebView.

**Jsoup Library**

In this section, we used the Jsoup library

to retrieve data for a specific feature.

**Navigation**

Here, we navigated through our

integrated feature.



<a name="br7"></a> 



<a name="br8"></a> 

**All Notice**

Here, we divided it into three sections: notices, press

releases, and tenders.

Moreover, we understood the importance of efficient

information retrieval. Therefore, alongside these

sections, we integrated a powerful search feature. This

feature empowered users to swiftly locate specific

content, ensuring that they could access the most

relevant information with ease.



<a name="br9"></a> 

**PDF or IMG view**

Here, we decode the URL to check whether it's a PDF or

an image, and then we integrate the corresponding view

into a single activity.

For PDFs, we use [**Barteksc**](https://github.com/barteksc/AndroidPdfViewer)[** ](https://github.com/barteksc/AndroidPdfViewer)[Android**](https://github.com/barteksc/AndroidPdfViewer)[** ](https://github.com/barteksc/AndroidPdfViewer)[PdfViewer](https://github.com/barteksc/AndroidPdfViewer)**.**

For images, we use [**PhotoView](https://github.com/Baseflow/PhotoView#photoview)**.**

Additionally, we provide a download option for saving

the PDFs or images.



<a name="br10"></a> 

**Course Reg. & Add/Drop**

On this page, students can select their courses and

sections.

We integrate an advanced RecyclerView along with

state and an interface, which memorizes previously

selected course data and update state in

CourseRegistrationActivity using interface.



<a name="br11"></a> 

**Course Reg. & Add/Drop**

Here, we display selected courses

using a popup window.

After registration is complete, redirect to this page

and display all registered courses.



<a name="br12"></a> 

**TER & CER**



<a name="br13"></a> 



<a name="br14"></a> 



<a name="br15"></a> 

**Browser System**

We integrate our own simple browser with existing

cookies, which means there's no need to log in again in

the browser. It also has a desktop mode feature.



<a name="br16"></a> 

**Open Source**

This project is available for all students who want to

learn Android development. This project helps students

learn core concepts and how to implement complex

functionality in Android.

Additionally, this app is available on the Play Store with

over 1000 active users who continuously use it.

Google Play Console



<a name="br17"></a> 

**Admin Panel**

This switch button is used to shut down the entire app.

This is used to control the app version.

The remaining elements are instrumental in keeping our

About page up-to-date. We believe in the importance

of transparent communication with our users, and this

page serves as a hub for sharing key information, our

mission, and the latest updates about our app.



<a name="br18"></a> 

**Extra Library**

●

●

●

●

●

●

●

●

●

●

●

●

Circle Image View

Table View

Bottom Navigation With Tab View

Spinner

PDF

Picasso

image Zoom in/Zoom out

Lottie

custom shape view

animated image view

chrome custom tab

Floating Drawer



<a name="br19"></a> 

**Thank you for**

**staying with us**

