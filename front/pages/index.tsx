import type { NextPage } from 'next'
import Link from 'next/link'
import Head from 'next/head'
import { Heading, HStack, Box } from '@chakra-ui/react'
import { PaymentTable, AddPaymentButton } from '../features/payments/Payments'
import { useState, useEffect } from 'react'
import useAppSelector from '../common/hooks/useAppSelector'
import useAppDispatch from '../common/hooks/useAppDispatch'
import {
  fetchPayments,
  resetCreating,
  previousMonth,
  nextMonth,
} from '../features/payments/paymentsSlice'
import { AppDispatch } from '../app/store'

const Home: NextPage = () => {
  const dispatch = useAppDispatch()
  const { yearDate } = useAppSelector((state) => state.payments)

  useEffect(() => {
    dispatch(resetCreating())
    dispatch(fetchPayments(yearDate))
  }, [dispatch, yearDate])

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
          {yearDate}
        </Heading>
        <HStack>
          <Box>
            <a onClick={() => dispatch(previousMonth())}>previous</a>
          </Box>
          <Box>
            <a onClick={() => dispatch(nextMonth())}>next</a>
          </Box>
        </HStack>
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
