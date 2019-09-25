stopwords=['ourselves', 'hers', 'between', 'yourself', 'but', 'again', 'there', 'about', 'once', 'during', 'out', 'very', 'having', 'with', 'they', 'own', 'an', 'be', 'some', 'for', 'do', 'its', 'yours', 'such', 'into', 'of', 'most', 'itself', 'other', 'off', 'is', 's', 'am', 'or', 'who', 'as', 'from', 'him', 'each', 'the', 'themselves', 'until', 'below', 'are', 'we', 'these', 'your', 'his', 'through', 'done', 'nor', 'me', 'were', 'her', 'more', 'himself', 'this', 'down', 'should', 'our', 'their', 'while', 'above', 'both', 'up', 'to', 'ours', 'had', 'she', 'all', 'no', 'when', 'at', 'any', 'before', 'them', 'same', 'and', 'been', 'have', 'in', 'will', 'on', 'does', 'yourselves', 'then', 'that', 'because', 'what', 'over', 'why', 'so', 'can', 'did', 'not', 'now', 'under', 'he', 'you', 'herself', 'has', 'just', 'where', 'too', 'only', 'myself', 'which', 'those', 'i', 'after', 'few', 'whom', 't', 'being', 'if', 'theirs', 'my', 'against', 'a', 'by', 'doing', 'it', 'how', 'further', 'was', 'here', 'than','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']
import csv
import string
f=open("/home/janhavi/Downloads/train_review (1).csv",'rU')
previews = []
nreviews = []
cyes = 0
cno = 0

def clear_punctuations(r):
	clear_string = ""
	for symbol in r:
		if symbol not in string.punctuation:
			clear_string += symbol
	return clear_string



for line in f:
	cells=line.split(",")
	r=cells[0].lower()
	review = clear_punctuations(r)
	review=r.split()
	#print str(cells[1])

	if str(cells[1])=="yes":
		previews.append(review)
		cyes+=1
	else:
		nreviews.append(review)
		cno+=1
nreviews = nreviews[1:]
print cyes
#print "..........................................."
print cno 
previewswstwrd = []
nreviewswstwrd = []
for i in previews:
	for j in i:
		if j not in stopwords:
			previewswstwrd.append(j)

for i in nreviews:
	for j in i:
		if j not in stopwords:
			nreviewswstwrd.append(j)

allwords = list(set(previewswstwrd)) + list(set(nreviewswstwrd))

#print (previewswstwrd)
#print (nreviewswstwrd)
#from cassandra.cluster import Cluster
#cluster = Cluster(protocol_version=3)
#session=cluster.connect()
#session.set_keyspace('tutorialspoint')
#h_name=raw_input('enter hotel name')

#result=session.execute("select count(*)from testdata where restaurant ='"+h_name+"'")
#count=result[0].count

#review=session.execute("select review from testdata where restaurant='"+h_name+"'")

ycount=0
ncount=0

#for z in range(count):
	
#testing code
	
a=raw_input("Enter review")

a = clear_punctuations(a)
a = a.lower()
sentence = a.split()
sentencewstpwrds = []
for i in sentence:
	if i not in stopwords:
		sentencewstpwrds.append(i)
#print sentencewstpwrds
#print float(len(previewswstwrd))
#print "Len of negative words is"
#print float(len(nreviewswstwrd))
#print "Cyes is"
#print cyes
print "Cno is............................"
print str(cyes) + " this is cyes"
print str(cno) + " this is cno"
psentence = float(cyes)/float(cyes+cno)
print psentence
print "Psentence"
for i in sentencewstpwrds:
	x = previewswstwrd.count(i)
	x = x+10
	x = float(x)/float(float(len(previewswstwrd))+float(len(allwords)))
	psentence = psentence*x
	print psentence
#print "...................................."
nsentence = float(cno)/float(cyes+cno)
for i in sentencewstpwrds:
	x = nreviewswstwrd.count(i)
	x = x+10
	x = float(x)/float(float(len(nreviewswstwrd))+float(len(allwords)))
	nsentence = nsentence*x
	print nsentence
if psentence > nsentence:
	print "yes"
	ycount +=1
else:
	print "no"
	ncount +=1

#print ycount
#print ncount



