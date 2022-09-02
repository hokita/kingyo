import Link from 'next/link'
import Head from 'next/head'
import { Heading, Button, Box } from '@chakra-ui/react'
import { NewPaymentForm } from '../../features/payments/Payments'
import { useState, useEffect } from 'react'
import useAppDispatch from '../../common/hooks/useAppDispatch'
import { createPayment } from '../../features/payments/paymentsSlice'

const New = () => {
  return (
    <>
      <Head>
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta
          name="apple-mobile-web-app-status-bar-style"
          content="black-translucent"
        />
      </Head>
      <Box m={5}>
        <Heading as="h1" size="xl" mb={3}>
          Kingyo
        </Heading>
        <Heading as="h2" size="lg" mb={3}>
          Create New Payment
        </Heading>
        <NewPaymentForm />
        <Box>
          <Link href="/">
            <a>cancel</a>
          </Link>
        </Box>
      </Box>
    </>
  )
}

export default New
