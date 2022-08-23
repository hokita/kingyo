import Link from 'next/link'
import { Heading, Button, Box } from '@chakra-ui/react'
import { NewPaymentForm } from '../../features/payments/Payments'
import { useState, useEffect } from 'react'
import useAppDispatch from '../../common/hooks/useAppDispatch'
import { createPayment } from '../../features/payments/paymentsSlice'

const New = () => {
  return (
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
  )
}

export default New
