<b>SUMMARY</b><p>
I have developed an application for students that share an apartment together to resolve the informal communication and monetary problem between roommates by integrating the following features: -
     • Add/ Maintain to-buy Lists: - Like maintain a shopping list.
     • Add /Maintain / Assign ad-hoc to-do task between flat mates
     </b>

<b>APPROACH</b>
Stack</br>
Backend:</br>
• Java</br>
• Spring Boot</br>
• Maven</br>
• Hibernate</br>
• JPA</br>
• Spring Security</br>

Database:</br>
• MySQL</br>

Object Model</br>
1. ROLE</br>
     a. Type</br>
2. Group</br>
    a. Id</br>
    b. Group Name</br>
    c. Group Description</br>
    d. Users <User></br>
3. User</br>
    a. Id</br>
    b. Username</br>
    c. Email</br>
    d. Password</br>
    e. Roles <Role></br>
    f. First name</br>
    g. Last name</br>
    h. Phone no</br>
    i. Group <Group></br>
    j. Tasks <TaskImpL></br>
4. Item <TaskImpl></br>
    a. Item Name</br>
    b. Item Price</br>
    c. Shared Users <User></br>
    d. Purchased on Date</br>
    e. Bought By user <User></br>


Token Based User Authentication Token-based authentication is a security technique that authenticates the users who attempt to log in to a server, a network, or some other secure system, using a security token provided by the server. Some Key benefits of token-based authentication are –</br>
 1. Cross-domain / CORS: Makes Authentication available across different domains as cookies don’t work across domains. </br>
 2. Stateless: There is no need to keep a session store, the token is a self-contained entity that conveys all the user information.</br>
  
Design Patterns used: </br>
1. Singleton Design Pattern </br>
2. Delegate Pattern</br>

Flow:</br>
Functionality: -</br></br>
• Register: -</br>
    o A user can register in the application by providing user’s first name, last name, phone no</br>
    o A user also enters password which later gets saved in the data base in an encrypted format using the Bcrypt library.</br>
• Login: -</br>
    o A registered user can only log into the system via the login screen</br>
    o Once logged in the user info is saved in the session.</br>
• Group Creation/joining Screen: -</br>
    o  A page where user can join or create and own a group</br>
    o User can send invites to other user to join the group via mail</br>
• Group Dashboard</br>
    o Priority Board page showing filtered scheduled tasks divided among all users.</br>
• Swap Board</br>
    o A Dashboard showing all the task that users among the group have put up for swap. So, if a User is having some how busy during the assigned task, he can raise a swap request and other users in the swap board can swap their tasks with the one on the swap board</br>
• Shopping List:</br>
 o Priority Board page showing filtered shopping items planned and completed.</br>
• Task List:</br>
    o Screen where user can find scheduled task assigned to them</br>
    o Users can mark task complete or edit them on this though this tab.</br>
• Account</br>
    o Profile User is able view and update his profile information from this screen. Users wishing to logout of the application can logout from this screen.</br>
    o Group Info Users can view group information like group name, description and the list of users who are part of the group. Here You can also invite Users to join the group via email.</br>
