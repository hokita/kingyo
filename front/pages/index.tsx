import type { NextPage } from 'next'
import Link from 'next/link'
import Head from 'next/head'
import { Heading } from '@chakra-ui/react'
import { Box } from '@chakra-ui/react'
import { PaymentTable, AddPaymentButton } from '../features/payments/Payments'
import { useState, useEffect } from 'react'
import useAppDispatch from '../common/hooks/useAppDispatch'
import { fetchPayments } from '../features/payments/paymentsSlice'
import { AppDispatch } from '../app/store'

const Home: NextPage = () => {
  const dispatch = useAppDispatch()

  useEffect(() => {
    dispatch(fetchPayments())
  }, [dispatch])

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
          Aug
        </Heading>
        <Box border="1px" borderColor="gray.200">
          <PaymentTable />
        </Box>
        <Box position="fixed" right="10" bottom="10">
          <Link href="/payments/new">
            <a>
              <AddPaymentButton />
            </a>
          </Link>
        </Box>
      </Box>
    </>
  )
}

export default Home
