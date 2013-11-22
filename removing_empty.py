data=[]
with open('empty') as g:
	for line in g:
		data.append(line)
count = 0
fil=[]
with open('tweet_author.txt') as f:
	for lin in f:
		fil.append(lin)
filtered=[fi for fi in fil if not fi in data]
with open('author.txt','w+') as p:
	for t in filtered:
		p.write(str(t))
		p.write('\n')
			
