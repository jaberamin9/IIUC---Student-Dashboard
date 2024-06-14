IIUC - Student  ![ref1]![ref2]![ref3]![ref4]![ref5]Dashboard   App 

Objective![](images/Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.006.png)

This app is built using the Android framework and primarily utilizes XML and Java languages. The main function of this app is to convert the IIUC website (only student panel) into an Android app.

**Here's a detailed explanation of how the app works:**

First, we closely observe the functionality of the IIUC website, ensuring that we capture all the essential features. Then, utilizing the **JSoup library**, we collect the necessary data and seamlessly integrate it into the app through both GET and POST methods.

**Why is this app needed, you might wonder?**

Well, the IIUC website currently lacks responsiveness for mobile users, resulting in an unsatisfactory user experience. Our app steps in to bridge this gap by enhancing the UI/UX specifically for mobile users, making it more intuitive and user-friendly than the website itself. Furthermore, it's worth noting that there are currently no mobile apps available for IIUC students on the Play Store, making our app a valuable addition to their digital resources.

In summary, our app not only addresses the usability issues of the IIUC website but also fills a gap in the availability of mobile applications tailored to the IIUC student community.

Android Firebase ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.007.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.008.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.009.png)**XML:** Android XML is primarily for defining the  Why do we use Firebase as a database?

visual layout of your app's UI.

- It is a real-time database.

**JAVA:** Java is used for writing the code that  ● To shut down the entire app. makes the app function and respond to user  ● To control the app version. actions.  ● To keep the about page up-to-date.

These two components work together to create a fully functional Android application.

Functionality![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.010.png)

- Notice
- Course Registration
- Course Add/Drop
- Payment History
- Result View
- Registration Summary
- Course Status
- Ter
- Profile
- Change Password
- Fee Calculator
- Browser
- PDF & IMG viewer
- Download PDF & IMG

Login & Forget Password![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.011.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.012.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.013.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.014.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.015.jpeg)

**Webview ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.016.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.017.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.018.png)**

Here, we showcased the entire website  using the built-in browser system using  a WebView. 

**Jsoup Library** 

In this section, we used the Jsoup library  to retrieve data for a specific feature. 

**Navigation** 

Here, we navigated through our  integrated feature. 

![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.019.png)   ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.020.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.021.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.022.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.023.jpeg)

All Notice ![ref6]![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.025.jpeg)

Here, we divided it into three sections: notices, press  releases, and tenders. 

Moreover, we understood the importance of efficient  information retrieval. Therefore, alongside these  sections, we integrated a powerful search feature. This  feature empowered users to swiftly locate specific  content, ensuring that they could access the most  relevant information with ease. 

PDF or IMG view ![ref6]![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.026.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.027.jpeg)

Here, we decode the URL to check whether it's a PDF or  an image, and then we integrate the corresponding  view into a single activity. 

For PDFs, we use **B[arteksc Android PdfViewer.** ](https://github.com/barteksc/AndroidPdfViewer)**For images, we use **P[hotoView.** ](https://github.com/Baseflow/PhotoView#photoview)**

Additionally, we provide a download option for saving  the PDFs or images. 

Course Reg. ![ref6]![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.028.jpeg)

On this page, students can select their courses and  sections. 

We integrate an advanced RecyclerView along with  state and an interface, which memorizes previously  selected course data and update state in  CourseRegistrationActivity using interface. 

Course Reg.![ref6]

![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.029.png) ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.030.jpeg)

Here, we display selected courses  After registration is complete, redirect to this page using a popup window. and display all registered courses.

![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.031.png) ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.032.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.033.jpeg)

![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.034.png) ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.035.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.036.jpeg)

Browser System ![ref6]![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.037.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.038.jpeg)

We integrate our own simple browser with existing  cookies, which means there's no need to log in again in  the browser. It also has a desktop mode feature. 

Open Source ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.039.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.040.jpeg)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.041.jpeg)

This project is available for all students who want to  learn Android development. This project helps students  learn core concepts and how to implement complex  functionality in Android. 

Additionally, this app is available on the Play Store with  over 1000 active users who continuously use it. 

` `![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.042.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.043.png)

Google Play Console

Admin Panel ![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.044.png)![](Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.045.jpeg)

This switch button is used to shut down the entire app. This is used to control the app version. 

The remaining elements are instrumental in keeping our  About page up-to-date. We believe in the importance  of transparent communication with our users, and this  page serves as a hub for sharing key information, our  mission, and the latest updates about our app. 

Extra Library![ref6]

- Circle Image View
- Table View
- Bottom Navigation With Tab View
- Spinner
- PDF
- Picasso
- image Zoom in/Zoom out
- Lottie
- custom shape view
- animated image view
- chrome custom tab
- Floating Drawer

Thank you for  ![ref1]![ref2]![ref3]![ref4]![ref5]staying with us 

[ref1]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.001.png
[ref2]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.002.png
[ref3]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.003.jpeg
[ref4]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.004.jpeg
[ref5]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.005.png
[ref6]: Aspose.Words.e9d37bbc-049d-49a2-94eb-231991d0dd8c.024.png
