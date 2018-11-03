#!/bin/bash
CPA=$(pwd)
echo "#!/bin/bash " > /bin/FeedInput
echo "export CPA=\"$CPA\"" >> /bin/FeedInput
echo "bash \"$CPA/FeedInput.sh\" \"\$1\"" >> /bin/FeedInput
chmod +x /bin/FeedInput
