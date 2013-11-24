import re
import json
filtered=[]
data = []
with open('tweets.json') as f:
	for line in f:
		data.append(json.loads(line))
#json_data=open('tweets.json').read()
for d in data:
	strings = re.findall(r'@([A-Za-z0-9_]+)', d['text'])
	if strings:
		filtered.append(d)
#twitter_username = re.compile(r'@([A-Za-z0-9_]+)')
#content = [x for x in content if not x.startswith(twitter_username)]
#print ''.join(filtered)
with open('tweet_author.json','w+') as g:
	for fil in filtered:
		json.dump(fil,g)
		g.write('\n')
