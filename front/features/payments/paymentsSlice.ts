import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { AppDispatch } from '../../app/store'
import axios from 'axios'

interface Payment {
  id: number
  description: String
  amount: number
  paidAt: String
  createdAt: String
  updatedAt: String
}

export interface PaymentsState {
  payments: Payment[]
  loading: 'idle' | 'pending' | 'succeeded' | 'failed'
}

const initialState = {
  payments: [],
  loading: 'idle',
} as PaymentsState

const paymentsSlice = createSlice({
  name: 'payments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(fetchPayments.pending, (state, action) => {
      state.loading = 'pending'
    })
    builder.addCase(fetchPayments.fulfilled, (state, action) => {
      state.payments = action.payload
      state.loading = 'succeeded'
    })
    builder.addCase(fetchPayments.rejected, (state, action) => {
      state.loading = 'failed'
    })
    builder.addCase(createPayment.fulfilled, (state, action) => {
      state.loading = 'succeeded'
    })
  },
})

export default paymentsSlice.reducer

// Thunk

export const fetchPayments = createAsyncThunk(
  'payments/getPayments',
  async () => {
    const result = await axios.get('http://localhost:8080/payments')
    return result.data
  }
)

export const createPayment = createAsyncThunk(
  'payments/createPayment',
  async (description: string) => {
    const body = {
      description,
      amount: 1000,
      paidAt: '2022-08-14T10:00:00+09:00',
    }
    const header = {
      'Content-Type': 'application/json',
    }
    await axios.post('http://localhost:8080/payments', body, {
      headers: header,
    })
  }
)
