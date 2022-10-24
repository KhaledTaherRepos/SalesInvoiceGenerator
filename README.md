# SalesInvoiceGenerator

A sales invoice is an accounting document that records a business transaction. It provides the business with a record of the services they’ve provided to a client, when the services were rendered and how much money the client owes the business.  Typically, a sales invoice will include a description of the service provided and the amount owed. Sales invoices are crucial to small-business accounting because they enable you to record transactions of your sales for bookkeeping purposes.

# fixed issue

The jcalender was causing a build issue since it's reference was an absolute path. Making it a relative path allowed the app to build on other computers successfully.
