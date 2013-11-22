import re
with open("tweet.txt") as f:
    content = f.readlines()
twitter_username = re.compile(r'@([A-Za-z0-9_]+)')
filtered=[]
for c in content:
	strings = re.findall(r'@([A-Za-z0-9_]+)', c)
	if strings:
		filtered.append(c)
#content = [x for x in content if not x.startswith(twitter_username)]
print ''.join(filtered)
