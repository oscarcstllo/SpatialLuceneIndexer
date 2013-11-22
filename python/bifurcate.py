import re
import json
data=[]
with open('tweet_author.json') as g:
	for line in g:
		data.append(json.loads(line))
text = [d['user'].encode('utf-8') for d in data]
with open('en.txt') as p:
    lines = p.read().splitlines()
pattern = re.compile(r'\b(' + r'|'.join(lines) + r')\b\s*')
with open('tweet_author.txt','w+') as f:
	for t in text:
		re.sub(r'\n', '', t)
		text = pattern.sub('', t)
		tr = re.sub(r'\shttps?:\/\/.*[\r\n]*', '', text, flags=re.MULTILINE)
		f.write(tr)
		f.write('\n')
