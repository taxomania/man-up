from __future__ import division
from __future__ import print_function
import logging
import threading

import ldap

_logger = logging.getLogger('LdapClient')

class UomLdapClient(threading.Thread):
    def __init__(self, host, port, mag_stripe_queue, manup_queue):
        super(UomLdapClient, self).__init__()
        self.daemon = True

        self._host = host
        self._port = port

        self._mag_stripe_queue = mag_stripe_queue
        self._response_queue = manup_queue

    def run(self):
        while True:
            student_card = self._mag_stripe_queue.get()
            response = self._query_ldap_server(student_card)
            if response:
                self._response_queue.put(response)
                self._mag_stripe_queue.task_done()

    def _query_ldap_server(self, student_card):
        conn = ldap.initialize('ldap://%s:%d' % (self._host, self._port))

        try:
            ldap_result = conn.search_s('', ldap.SCOPE_SUBTREE,
                '(umanMagStripe=%s)' % student_card)
        except ldap.SERVER_DOWN:
            _logger.error('Failed to connect to LDAP server.')
        else:
            if ldap_result and len(ldap_result[0]) >= 1:
                return ldap_result[0][1]
