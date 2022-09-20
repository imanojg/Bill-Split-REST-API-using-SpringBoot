<b>SUMMARY</b><p>
I have developed an application for students that share an apartment together to resolve the informal communication and monetary problem between roommates by integrating the following features: -
     • Add/ Maintain to-buy Lists: - Like maintain a shopping list.
     • Add /Maintain / Assign ad-hoc to-do task between flat mates
     </b>

<b>APPROACH</b>
Stack
Backend:
• Java
• Spring Boot
• Maven
• Hibernate
• JPA
• Spring Security

Database:
• MySQL

Object Model
1. ROLE
     a. Type
2. Group
    a. Id
    b. Group Name
    c. Group Description
    d. Users <User>
3. User
    a. Id
    b. Username
    c. Email
    d. Password
    e. Roles <Role>
    f. First name
    g. Last name
    h. Phone no
    i. Group <Group>
    j. Tasks <TaskImpL>
5. Item <TaskImpl>
    a. Item Name
    b. Item Price
    c. Shared Users <User>
    d. Purchased on Date
    e. Bought By user <User>


Token Based User Authentication Token-based authentication is a security technique that authenticates the users who attempt to log in to a server, a network, or some other secure system, using a security token provided by the server. Some Key benefits of token-based authentication are –
 1. Cross-domain / CORS: Makes Authentication available across different domains as cookies don’t work across domains. 
 2. Stateless: There is no need to keep a session store, the token is a self-contained entity that conveys all the user information
  
Design Patterns used: 
1. Singleton Design Pattern 
2. Delegate Pattern

Flow:
Functionality: -
• Register: -
    o A user can register in the application by providing user’s first name, last name, phone no
    o A user also enters password which later gets saved in the data base in an encrypted format using the Bcrypt library.
• Login: -
    o A registered user can only log into the system via the login screen
    o Once logged in the user info is saved in the session.
• Group Creation/joining Screen: -
    o  A page where user can join or create and own a group
    o User can send invites to other user to join the group via mail
• Group Dashboard
    o Priority Board page showing filtered scheduled tasks divided among all users.
• Swap Board
    o A Dashboard showing all the task that users among the group have put up for swap. So, if a User is having some how busy during the assigned task, he can raise a swap request and other users in the swap board can swap their tasks with the one on the swap board
• Shopping List:

    o Priority Board page showing filtered shopping items planned and completed.
• Task List:
    o Screen where user can find scheduled task assigned to them
    o Users can mark task complete or edit them on this though this tab.
• Account
    o Profile User is able view and update his profile information from this screen. Users wishing to logout of the application can logout from this screen.
    o Group Info Users can view group information like group name, description and the list of users who are part of the group. Here You can also invite Users to join the group via email.
