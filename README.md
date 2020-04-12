<h3>Reactive-NoSQL-Test</h3>
<h5>Test Spring Data Reactive Mongo, Spring Data Reactive Cassandra, and Scala Phantom</h5>

<h4>mongo-test</h4>
<ol>
<li>Spring (Boot) Data ReactiveMongo with multiple databases and collections within each database</li>
<li>Need ReactiveMongoTemplate for each database with configuration enabled for each repository</li>
</ol>

<h4>cassandra-test</h4>
<ol>
<li>Spring (Boot) Data ReactiveCassandra with one KeySpace and multiple tables in the KeySpace</li>
<li>Each entity has corresponding table in Cassandra KeySpace and corresponding repository and service in Spring Data</li>
</ol>

<h4>mongo-to-cassandra</h4>
<ol>
<li>Needed to transfer data from MongoDB collection to Cassandra Table and wanted to test Scala Phantom driver</li>
<li>Test routine written in Scala with Alpakka MongoDB connector and Phantom Driver</li>
</ol>

<h5>Note: there is minimal documentation for each of these test applications and there are considerable confusing blogs
 regarding these methods/drivers. Hopefully, these implementations are clear and simplified and can clarify omissions in 
 documentation.</h5>