import type { NextPage } from 'next'
import Link from 'next/link'
import { Heading } from '@chakra-ui/react'
import { Box } from '@chakra-ui/react'
import { Table as PaymentTable, AddButton } from '../features/payments/Payments'
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
            <AddButton />
          </a>
        </Link>
      </Box>
    </Box>
  )
}

export default Home
