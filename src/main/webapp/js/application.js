var cometd;
var dc = '/service/desktopConnection';

var attempt = {
	LOAD_MEDIA: 100
};

var command = {
	LOAD_MEDIA: 100,
	WAIT_FOR_PROCESS: 101
};

var MediaTypes = {
	VIDEO: 200,
	IMAGE: 201
};

(function($)
{
    cometd = $.cometd;
    

    $(document).ready(function()
    {
        function _connectionEstablished()
        {
            //$('#body').append('<div>CometD Connection Established</div>');
            console.log("CometD Connection Established");
        }

        function _connectionBroken()
        {
            //$('#body').append('<div>CometD Connection Broken</div>');
            console.log("CometD Connection Broken");
        }

        function _connectionClosed()
        {
            //$('#body').append('<div>CometD Connection Closed</div>');
            console.log("CometD Connection Closed");
        }

        // Function that manages the connection status with the Bayeux server
        var _connected = false;
        function _metaConnect(message)
        {
            if (cometd.isDisconnected())
            {
                _connected = false;
                _connectionClosed();
                return;
            }

            var wasConnected = _connected;
            _connected = message.successful === true;
            if (!wasConnected && _connected)
            {
                _connectionEstablished();
            }
            else if (wasConnected && !_connected)
            {
                _connectionBroken();
            }
        }

        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(handshake)
        {
            if (handshake.successful === true)
            {
                cometd.batch(function()
                {
                    cometd.subscribe('/desktopConnection', function(message)
                    {

                        console.log("HEY FROM SERVER: ");
                        handleDesktopServiceMessage(message.data.response);
                    });
                    // Publish on a service channel since the message is for the server only
                    cometd.publish(dc, { name: 'World' });
                });
            }
        }

        // Disconnect when the page unloads
        $(window).unload(function()
        {
            cometd.disconnect(true);
        });

        var cometURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
        cometd.configure({
            url: cometURL,
            logLevel: 'debug'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
    });
})(jQuery);
