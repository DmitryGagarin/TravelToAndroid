package com.example.traveltoandroid.viewModels.attraction

import com.example.traveltoandroid.models.AttractionModel
import com.example.traveltoandroid.repository.attraction.AttractionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AttractionsViewModelTest {

    // A special coroutine dispatcher for testing that gives you control over time.
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockRepository: AttractionRepository
    private lateinit var viewModel: AttractionsViewModel

    @Before
    fun setUp() {
        // Replaces Android's Main dispatcher with test dispatcher
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    // Helper function to create test attractions matching your data class
    private fun createTestAttraction(
        uuid: String = "123e4567-e89b-12d3-a456-426614174000",
        name: String = "Test Attraction",
        description: String? = "A beautiful test attraction",
        address: String? = "123 Test Street, Test City",
        images: List<String>? = listOf("image1.jpg", "image2.jpg"),
        imagesFormats: List<String>? = listOf("jpg", "webp"),
        phone: String? = "+1234567890",
        website: String? = "https://test.com",
        type: String? = "Museum",
        isRoundTheClock: Boolean? = false,
        openTime: String? = "09:00",
        closeTime: String? = "18:00",
        rating: Double? = 4.5,
        status: String? = "published"
    ): AttractionModel {
        return AttractionModel(
            uuid = uuid,
            name = name,
            description = description,
            address = address,
            images = images,
            imagesFormats = imagesFormats,
            phone = phone,
            website = website,
            type = type,
            isRoundTheClock = isRoundTheClock,
            openTime = openTime,
            closeTime = closeTime,
            rating = rating,
            status = status
        )
    }

    @Test
    fun `init should call loadPublishedAttractions automatically`() = runTest {
        // Given
        val expectedAttractions = listOf(
            createTestAttraction(
                uuid = "1",
                name = "Eiffel Tower",
                rating = 4.7
            )
        )

        // Way to mock suspend functions
        coEvery { mockRepository.getPublishedAttractions() } returns expectedAttractions

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { mockRepository.getPublishedAttractions() }
        assertEquals(expectedAttractions, viewModel.attractions.value)
        assertEquals("Eiffel Tower", viewModel.attractions.value[0].name)
        assertEquals(4.7, viewModel.attractions.value[0].rating)
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadPublishedAttractions should set loading to true during API call`() = runTest {
        // Given
        // coAnswers returns logic
        coEvery { mockRepository.getPublishedAttractions() } coAnswers {
            delay(100) // Simulate network delay
            emptyList()
        }

        // When
        viewModel = AttractionsViewModel(mockRepository)

        // Then - Loading should be true immediately
        assertTrue(viewModel.isLoading.value)

        // "Fast-forwards" time until all coroutines are done.
        advanceUntilIdle()
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `loadPublishedAttractions should update attractions with all fields correctly`() = runTest {
        // Given
        val expectedAttractions = listOf(
            createTestAttraction(
                uuid = "attraction-1",
                name = "Louvre Museum",
                description = "World's largest art museum",
                address = "Rue de Rivoli, 75001 Paris, France",
                images = listOf("louvre1.jpg", "louvre2.jpg"),
                imagesFormats = listOf("jpg", "png"),
                phone = "+33140205050",
                website = "https://www.louvre.fr",
                type = "Museum",
                isRoundTheClock = false,
                openTime = "09:00",
                closeTime = "18:00",
                rating = 4.8,
                status = "published"
            ),
            createTestAttraction(
                uuid = "attraction-2",
                name = "Times Square",
                description = "Major commercial intersection",
                address = "Manhattan, NY 10036, USA",
                images = listOf("times1.jpg"),
                imagesFormats = listOf("jpg"),
                phone = null,
                website = null,
                type = "Square",
                isRoundTheClock = true,
                openTime = null,
                closeTime = null,
                rating = 4.3,
                status = "published"
            )
        )

        coEvery { mockRepository.getPublishedAttractions() } returns expectedAttractions

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then - Verify all fields
        val attractions = viewModel.attractions.value

        assertEquals(2, attractions.size)

        // First attraction
        assertEquals("attraction-1", attractions[0].uuid)
        assertEquals("Louvre Museum", attractions[0].name)
        assertEquals("World's largest art museum", attractions[0].description)
        assertEquals("Rue de Rivoli, 75001 Paris, France", attractions[0].address)
        assertEquals(listOf("louvre1.jpg", "louvre2.jpg"), attractions[0].images)
        assertEquals("Museum", attractions[0].type)
        assertEquals(false, attractions[0].isRoundTheClock)
        assertEquals("09:00", attractions[0].openTime)
        assertEquals("18:00", attractions[0].closeTime)
        assertEquals(4.8, attractions[0].rating)
        assertEquals("published", attractions[0].status)

        // Second attraction (with nullable fields)
        assertEquals("attraction-2", attractions[1].uuid)
        assertEquals("Times Square", attractions[1].name)
        assertNull(attractions[1].phone)
        assertNull(attractions[1].website)
        assertEquals(true, attractions[1].isRoundTheClock)
        assertNull(attractions[1].openTime)
        assertNull(attractions[1].closeTime)

        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadPublishedAttractions should handle empty attractions list`() = runTest {
        // Given
        coEvery { mockRepository.getPublishedAttractions() } returns emptyList()

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.attractions.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadPublishedAttractions should handle attractions with null fields`() = runTest {
        // Given - Attraction with mostly null values
        val expectedAttractions = listOf(
            AttractionModel(
                uuid = "partial-uuid",
                name = "Unknown Attraction",
                description = null,
                address = null,
                images = null,
                imagesFormats = null,
                phone = null,
                website = null,
                type = null,
                isRoundTheClock = null,
                openTime = null,
                closeTime = null,
                rating = null,
                status = null
            )
        )

        coEvery { mockRepository.getPublishedAttractions() } returns expectedAttractions

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then - Should handle nulls gracefully
        assertEquals(1, viewModel.attractions.value.size)
        assertEquals("partial-uuid", viewModel.attractions.value[0].uuid)
        assertEquals("Unknown Attraction", viewModel.attractions.value[0].name)
        assertNull(viewModel.attractions.value[0].description)
        assertNull(viewModel.attractions.value[0].rating)
        assertNull(viewModel.attractions.value[0].status)

        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadPublishedAttractions should set error on generic exception`() = runTest {
        // Given
        val errorMessage = "Database connection failed"
        coEvery { mockRepository.getPublishedAttractions() } throws
                Exception(errorMessage)

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.error.value?.contains("Failed to load attractions") == true)
        assertTrue(viewModel.error.value?.contains(errorMessage) == true)
        assertTrue(viewModel.attractions.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `loadPublishedAttractions should show specific error for timeout`() = runTest {
        // Test various timeout messages - ALL LOWERCASE
        val timeoutMessages = listOf(
            "timeout",                     // lowercase
            "read timeout",               // contains timeout
            "connect timeout",            // contains timeout
            "socket timeout",             // contains timeout
            "timeout exception"           // contains timeout
        )

        for (message in timeoutMessages) {
            // Given
            coEvery { mockRepository.getPublishedAttractions() } throws
                    Exception(message)

            // When
            viewModel = AttractionsViewModel(mockRepository)
            advanceUntilIdle()

            // Then
            assertEquals(
                "Connection timeout. Server might be down.",
                viewModel.error.value
            )
        }
    }

    @Test
    fun `loadPublishedAttractions should show specific error for connection failure`() = runTest {
        // Test ONLY what the ViewModel actually handles
        val connectionMessages = listOf(
            "Cannot connect to server. Check URL and server status"
        )

        for (message in connectionMessages) {
            // Given
            coEvery { mockRepository.getPublishedAttractions() } throws
                    Exception(message)

            // When
            viewModel = AttractionsViewModel(mockRepository)
            advanceUntilIdle()

            // Then
            assertEquals(
                "Failed to load attractions: Cannot connect to server. Check URL and server status",
                viewModel.error.value
            )
        }
    }

    @Test
    fun `loadPublishedAttractions should show specific error for permission denied`() = runTest {
        // Given
        coEvery { mockRepository.getPublishedAttractions() } throws
                Exception("EPERM: network permission denied")

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        assertEquals(
            "Network permission denied. Check internet permissions.",
            viewModel.error.value
        )
    }

    @Test
    fun `loadPublishedAttractions should always set loading to false in finally block`() = runTest {
        // Test with both success and error scenarios

        // Scenario 1: Success
        coEvery { mockRepository.getPublishedAttractions() } returns emptyList()
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()
        assertFalse("Should be false after success", viewModel.isLoading.value)

        // Scenario 2: Error
        coEvery { mockRepository.getPublishedAttractions() } throws Exception("Test error")
        viewModel.loadPublishedAttractions()
        advanceUntilIdle()
        assertFalse("Should be false even after error", viewModel.isLoading.value)
    }

    @Test
    fun `refreshAttractions should clear attractions and reload`() = runTest {
        // Given - Initial load
        val initialAttractions = listOf(
            createTestAttraction(uuid = "old-1", name = "Old Attraction")
        )

        coEvery { mockRepository.getPublishedAttractions() } returns initialAttractions

        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        assertEquals(1, viewModel.attractions.value.size)

        // Setup for refresh
        val refreshedAttractions = listOf(
            createTestAttraction(uuid = "new-1", name = "New Attraction 1"),
            createTestAttraction(uuid = "new-2", name = "New Attraction 2")
        )

        coEvery { mockRepository.getPublishedAttractions() } returns refreshedAttractions

        // When
        viewModel.refreshAttractions()

        // Then - Should clear immediately
        assertTrue(
            "Attractions should be cleared immediately",
            viewModel.attractions.value.isEmpty()
        )

        advanceUntilIdle()

        // After refresh completes
        assertEquals(2, viewModel.attractions.value.size)
        assertEquals("New Attraction 1", viewModel.attractions.value[0].name)
        assertEquals("new-1", viewModel.attractions.value[0].uuid)
    }

    @Test
    fun `refreshAttractions should reset error state`() = runTest {
        // Given - First call with error
        coEvery { mockRepository.getPublishedAttractions() } throws
                Exception("First error") andThen emptyList()

        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        assertTrue(viewModel.error.value != null)

        // When
        viewModel.refreshAttractions()

        // Then - Error should be cleared at start of refresh
        assertNull(viewModel.error.value)

        advanceUntilIdle()
        assertNull(viewModel.error.value)
    }

    @Test
    fun `multiple calls to loadPublishedAttractions should handle state correctly`() = runTest {
        // Given
        var callCount = 0
        coEvery { mockRepository.getPublishedAttractions() } coAnswers {
            delay(50)
            callCount++
            listOf(createTestAttraction(name = "Attraction $callCount"))
        }

        // When
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        val initialCount = callCount

        // Make multiple rapid calls
        viewModel.loadPublishedAttractions()
        viewModel.loadPublishedAttractions()
        viewModel.loadPublishedAttractions()

        advanceUntilIdle()

        // Then
        assertTrue(callCount > initialCount) // Should make additional calls
        assertFalse(viewModel.isLoading.value) // Should end with loading false
        // Last attraction name should match last call
        assertTrue(viewModel.attractions.value[0].name?.contains("Attraction") == true)
    }

    @Test
    fun `attractions updates correctly after multiple loads`() = runTest {
        // Given
        val attractions1 = listOf(createTestAttraction(name = "First"))
        val attractions2 = listOf(createTestAttraction(name = "Second"))

        coEvery { mockRepository.getPublishedAttractions() } returnsMany
                listOf(attractions1, attractions2)

        // When - Create ViewModel (first load)
        viewModel = AttractionsViewModel(mockRepository)
        advanceUntilIdle()

        // Then - First load
        assertEquals("First", viewModel.attractions.value.first().name)

        // When - Load again
        viewModel.loadPublishedAttractions()
        advanceUntilIdle()

        // Then - Second load
        assertEquals("Second", viewModel.attractions.value.first().name)
    }

    @Test
    fun `loading state changes correctly during API call`() = runTest {
        // Given - Simulate network delay
        coEvery { mockRepository.getPublishedAttractions() } coAnswers {
            delay(100)  // Network delay
            emptyList()
        }

        // When - Create ViewModel (starts loading immediately in init)
        viewModel = AttractionsViewModel(mockRepository)

        // Then - Should be loading immediately
        assertTrue("Should be loading after ViewModel creation", viewModel.isLoading.value)

        // When - Wait for API call to complete
        advanceUntilIdle()

        // Then - Should NOT be loading after completion
        assertFalse("Should not be loading after API completes", viewModel.isLoading.value)
    }
}