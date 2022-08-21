import Link from 'next/link'
import { Heading, Button, Box } from '@chakra-ui/react'
import { DiscriptionForm } from '../../features/payments/Payments'
import { useState, useEffect } from 'react'
import useAppDispatch from '../../common/hooks/useAppDispatch'
import { createPayment } from '../../features/payments/paymentsSlice'

const New = () => {
  const [description, setDiscription] = useState('')
  const dispatch = useAppDispatch()

  return (
    <Box m={5}>
      <Heading as="h1" size="xl" mb={3}>
        Kingyo
      </Heading>
      <Heading as="h2" size="lg" mb={3}>
        Create New Payment
      </Heading>
      <Box border="1px" borderColor="gray.200" mb={3}>
        <DiscriptionForm
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setDiscription(e.target.value)
          }
        />
      </Box>
      <Box>
        <Button
          colorScheme="blue"
          onClick={() => dispatch(createPayment(description))}
        >
          Submit
        </Button>
      </Box>
      <Box>
        <Link href="/">
          <a>cancel</a>
        </Link>
      </Box>
    </Box>
  )
}

export default New
